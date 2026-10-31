package com.project.fitness.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http){

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authorizeRequests -> authorizeRequests
// ADMIN ROUTE
                                .requestMatchers("/api/admin/**")
                                .hasRole("ADMIN")
// PUBLIC ROUTE
                                .requestMatchers("/api/auth/**").permitAll()

                                .anyRequest()
                                .authenticated()
                );


        return http.build();
    }

}
