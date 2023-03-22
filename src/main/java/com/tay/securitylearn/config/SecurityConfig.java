package com.tay.securitylearn.config;

import com.tay.securitylearn.enums.UserPermission;
import com.tay.securitylearn.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.tay.securitylearn.enums.UserPermission.*;
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
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                // 配置有前后顺序区别，配置需要注意前后顺序
                .antMatchers("/","index","/css/*","/js").permitAll()
                .antMatchers("/api/v1/student/**").hasRole(STUDENT.name())
                // 基于角色
                /*.antMatchers(HttpMethod.DELETE, "/management/api/**").hasAuthority(COURSE_WRITE.name())
                .antMatchers(HttpMethod.PUT, "/management/api/**").hasAuthority(COURSE_WRITE.name())
                .antMatchers(HttpMethod.POST, "/management/api/**").hasAuthority(COURSE_WRITE.name())*/
                // 基于权限
                .antMatchers(HttpMethod.DELETE, "/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.PUT, "/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.POST, "/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.GET, "/management/api/**").hasAnyRole(ADMIN.name(), ADMIN_TRAINER.name())
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {

        UserDetails tayDetails = User.builder()
                .username("anna")
                .password(passwordEncoder.encode("1234"))
                // 基于角色  ROLE_STUDENT
                //.roles(STUDENT.name())
                // 基于权限
                .authorities(STUDENT.getGrantedAuthority())
                .build();

        UserDetails jayDetails = User.builder()
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
        return new InMemoryUserDetailsManager(tayDetails, jayDetails, tomDetail);
    }
}
