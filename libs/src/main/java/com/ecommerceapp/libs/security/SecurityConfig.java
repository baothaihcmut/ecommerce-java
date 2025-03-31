package com.ecommerceapp.libs.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable()) // Disable CSRF if using stateless API
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Allow all requests (auth handled at API Gateway)
                )
                .addFilterBefore(new ExtractUserInfoFilter(), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
