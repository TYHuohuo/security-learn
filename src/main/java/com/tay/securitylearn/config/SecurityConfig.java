package com.tay.securitylearn.config;

import com.tay.securitylearn.jwt.JwtConfig;
import com.tay.securitylearn.jwt.JwtTokenVerifier;
import com.tay.securitylearn.jwt.JwtUsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;

import static com.tay.securitylearn.enums.UserRole.*;

/**
 * <p>
 * security配置类
 * </p>
 *
 * @author tay
 * @since 2022-05-15
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private PasswordEncoder passwordEncoder;
    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;

    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder, SecretKey secretKey, JwtConfig jwtConfig, UserDetailsService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.secretKey = secretKey;
        this.jwtConfig = jwtConfig;
        this.userDetailsService = userDetailsService;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // token登录配置
                .addFilter(new JwtUsernamePasswordAuthenticationFilter(authenticationManager(), jwtConfig, secretKey))
                // token校验过滤器
                .addFilterAfter(new JwtTokenVerifier(secretKey, jwtConfig), JwtUsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                // 配置有前后顺序区别，配置需要注意前后顺序
                .antMatchers("/","index","/css/*","/js").permitAll()
                .antMatchers("/api/v1/student/**").hasRole(STUDENT.name())
                .anyRequest()
                .authenticated();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider () {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }
    /*
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {

        /*UserDetails annaDetails = User.builder()
                .username("anna")
                .password(passwordEncoder.encode("1234"))
                // 基于角色  ROLE_STUDENT
                //.roles(STUDENT.name())
                // 基于权限
                .authorities(STUDENT.getGrantedAuthority())
                .build();

        UserDetails lindaDetails = User.builder()
                .username("linda")
                .password(passwordEncoder.encode("1234"))
                //.roles(ADMIN.name()) // ROLE_ADMIN
                .authorities(ADMIN.getGrantedAuthority())
                .build();

        UserDetails tomDetail = User.builder()
                .username("tom")
                .password(passwordEncoder.encode("1234"))
                //.roles(ADMIN_TRAINER.name()) // ROLE_ADMIN_TRAINER
                .authorities(ADMIN_TRAINER.getGrantedAuthority())
                .build();
        return new InMemoryUserDetailsManager(annaDetails, lindaDetails, tomDetail);
    }
    */
}
