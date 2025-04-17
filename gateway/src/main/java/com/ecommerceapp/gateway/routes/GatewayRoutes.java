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
                                .filter(authFilter())
                                .circuitBreaker(c -> c
                                        .setName("authCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/auth")))
                        .uri(properties.getRoutes().getAuthService()))
                .route("users", r -> r
                        .path(properties.getPrefix() + "/users/**")
                        .filters(f -> f
                                .stripPrefix(2)
                                .filter(authFilter())
                                .circuitBreaker(c -> c
                                        .setName("authCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/users")))
                        .uri(properties.getRoutes().getUsersService()))
                .route("shops", r -> r
                        .path(properties.getPrefix() + "/shops/**")
                        .filters(f -> f
                                .stripPrefix(2)
                                .filter(authFilter())
                                .circuitBreaker(c -> c
                                        .setName("authCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/shops")))
                        .uri(properties.getRoutes().getShopsService()))
                .route("products", r -> r
                        .path(properties.getPrefix() + "/products/**")
                        .filters(f -> f
                                .stripPrefix(2)
                                .filter(authFilter())
                                .circuitBreaker(c -> c
                                        .setName("authCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/products")))
                        .uri(properties.getRoutes().getProductsService()))
                .route("orders", r -> r
                        .path(properties.getPrefix() + "/orders/**")
                        .filters(f -> f
                                .stripPrefix(2)
                                .filter(authFilter())
                                .circuitBreaker(c -> c
                                        .setName("authCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/orders")))
                        .uri(properties.getRoutes().getOrdersService()))
                .route("shipment", r -> r
                        .path(properties.getPrefix() + "/shipment/**")
                        .filters(f -> f
                                .stripPrefix(2)
                                .filter(authFilter())
                                .circuitBreaker(c -> c
                                        .setName("authCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/shipment")))
                        .uri(properties.getRoutes().getShipmentService()))
                .route("payment", r -> r
                        .path(properties.getPrefix() + "/payment/**")
                        .filters(f -> f
                                .stripPrefix(2)
                                .filter(authFilter())
                                .circuitBreaker(c -> c
                                        .setName("authCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/payment")))
                        .uri(properties.getRoutes().getPaymentService()))
                .route("shops-api-docs", r -> r
                        .path(properties.getPrefix() + "/shops-service/api-docs")
                        .filters(f -> f
                                .stripPrefix(3))
                        .uri(properties.getRoutes().getShopsService()))
                .route("users-api-docs", r -> r
                        .path(properties.getPrefix() + "/users-service/api-docs")
                        .filters(f -> f.stripPrefix(3))
                        .uri(properties.getRoutes().getUsersService()))
                .route("orders-api-docs", r -> r
                        .path(properties.getPrefix() + "/orders-service/api-docs")
                        .filters(f -> f
                                .stripPrefix(3))
                        .uri(properties.getRoutes().getOrdersService()))
                .build();
    }

    private GatewayFilter authFilter() {
        return authFilter.apply(new AuthFilter.Config(
                properties.getPrefix(),
                properties.getOpenApis()));
    }
}
