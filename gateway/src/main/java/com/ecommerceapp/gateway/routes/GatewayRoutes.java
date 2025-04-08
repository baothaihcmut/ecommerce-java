package com.ecommerceapp.gateway.routes;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ecommerceapp.gateway.filters.AuthFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableConfigurationProperties(GatewayProperties.class)
@RequiredArgsConstructor
public class GatewayRoutes {
    private final GatewayProperties properties;
    private final AuthFilter authFilter;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth", r -> r
                        .path(properties.getPrefix() + "/auth/**")
                        .filters(f -> f
                                .stripPrefix(2)
                                .filter(authFilter()))
                        .uri(properties.getRoutes().getAuthService()))
                .route("shops", r -> r
                        .path(properties.getPrefix() + "/shops/**")
                        .filters(f -> f
                                .stripPrefix(2)
                                .filter(authFilter()))
                        .uri(properties.getRoutes().getShopsService()))
                .route("shops-api-docs", r -> r
                        .path(properties.getPrefix() + "/shops-service/api-docs")
                        .filters(f -> f
                                .stripPrefix(3))
                        .uri(properties.getRoutes().getShopsService()))
                .route("users-api-docs", r -> r
                        .path(properties.getPrefix() + "/users-service/api-docs")
                        .filters(f -> f.stripPrefix(3))
                        .uri(properties.getRoutes().getUsersService()))
                .build();
    }

    private GatewayFilter authFilter() {
        return authFilter.apply(new AuthFilter.Config(
                properties.getPrefix(),
                properties.getOpenApis()));
    }
}
