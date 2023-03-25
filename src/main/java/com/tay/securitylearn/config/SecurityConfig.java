package com.tay.securitylearn.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

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

    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                // 配置有前后顺序区别，配置需要注意前后顺序
                .antMatchers("/","index","/css/*","/js").permitAll()
                .antMatchers("/api/v1/student/**").hasRole(STUDENT.name())
                .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    // 默认登录成功页
                    .defaultSuccessUrl("/courses", true)
                    // 自定义 用户名、密码 参数名称
                    .passwordParameter("password")
                    .usernameParameter("username")
                .and()
                .rememberMe()
                    .tokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(21))
                    .key("security-important-key")
                    // 自定义 记住我 参数名称
                    .rememberMeParameter("remember-me")
                .and()
                // 退出登录，清除cookie session等信息
                .logout()
                .logoutUrl("/logout")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID", "remember-me")
                .logoutSuccessUrl("/login");
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
