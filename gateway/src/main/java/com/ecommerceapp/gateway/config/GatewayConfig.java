package com.ecommerceapp.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ecommerceapp.gateway.filters.AuthFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableHystrix
@RequiredArgsConstructor
public class GatewayConfig {
    private final AuthFilter authFilter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(
                        "auth-service",
                        r -> r.path("/auth/**")
                                .filters(f -> f.filter(authFilter))
                                .uri("lb://users"))
                .build();

    }
}
