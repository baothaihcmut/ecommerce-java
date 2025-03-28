package com.ecommerceapp.gateway.filters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.ecommerceapp.gateway.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import reactor.core.publisher.Mono;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;

@Slf4j
@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {
    private final ObjectMapper objectMapper;
    private final JwtUtil jwtUtil;

    public AuthFilter(ObjectMapper objectMapper, JwtUtil jwtUtil) {
        super(Config.class);
        this.objectMapper = objectMapper;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if (isSecured(request,
                    config.getOpenApiEndpoints().stream().map(uri -> config.getPrefix() + uri).toList())) {
                if (isAuthMissing(request)) {
                    return onError(exchange, "Missing token", HttpStatus.UNAUTHORIZED);
                }

                String token = getAuthHeader(request);
                if (jwtUtil.isInvalid(token)) {
                    return onError(exchange, "Token is invalid", HttpStatus.UNAUTHORIZED);
                }

                populateRequestWithHeaders(exchange, token);
            }
            return chain.filter(exchange);
        };
    }

    private boolean isSecured(ServerHttpRequest request, List<String> openApis) {
        return openApis.stream().noneMatch(uri -> request.getURI().getPath().contains(uri));
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION);
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
    }

    private void populateRequestWithHeaders(ServerWebExchange exchange, String token) {
        Claims claims = jwtUtil.getAllClaimsFromToken(token);
        exchange.getRequest().mutate()
                .header("X-User-Id", String.valueOf(claims.get("userId")))
                .header("X-Is-Shop-Owner-Active", String.valueOf(claims.get("userId")))
                .build();
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus status) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("message", err);
        errorResponse.put("data", null);

        try {
            byte[] bytes = objectMapper.writeValueAsBytes(errorResponse);
            DataBuffer buffer = response.bufferFactory().wrap(bytes);
            return response.writeWith(Mono.just(buffer));
        } catch (Exception e) {
            return response.setComplete();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Config {
        private String prefix;
        private List<String> openApiEndpoints;
    }
}
