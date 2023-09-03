package com.blogapplication.securityconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){
        JdbcUserDetailsManager jdbcUserDetailsManager=new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "Select username,password,active from users where username=? ");
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "Select username,role from users where username=?");
        return jdbcUserDetailsManager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers("/").permitAll()
                                .requestMatchers("/showFormForAdd").hasAnyRole("author", "admin") // Allow author and admin
                                .requestMatchers("/draftPost").hasAnyRole("author", "admin") // Allow author and admin
                                .requestMatchers("/save").hasAnyRole("author", "admin") // Allow author and admin
                                .requestMatchers("/delete").hasAnyRole("author", "admin") // Allow author and admin
                                .requestMatchers("/showFormForUpdate").hasAnyRole("author", "admin") // Allow author and admin

                                .anyRequest().permitAll())
                .formLogin(form -> form
                        .loginPage("/showMyLoginPage")
                        .loginProcessingUrl("/authenticateTheUser")
                        .permitAll()
                )
                .logout(logout -> logout.permitAll())
                .exceptionHandling(configurer ->
                        configurer.accessDeniedPage("/access-denied"));
        return http.build();
    }

}