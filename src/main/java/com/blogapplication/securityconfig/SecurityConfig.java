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
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(configurer ->
                        configurer
//                                .requestMatchers("/listOfPosts").permitAll()
//                                .requestMatchers("/comments/save").permitAll()
                                .requestMatchers("/").permitAll()
                                .requestMatchers("/showFormForAdd").hasRole("author")
                                .requestMatchers("/draftPost").hasRole("author")
                                .requestMatchers("/save").hasRole("author")

//                                .requestMatchers("/draftPost").hasAnyRole("author","admin")
                                .requestMatchers("/delete").hasRole("author")
                                .requestMatchers("/showFormForUpdate").hasRole("author")

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