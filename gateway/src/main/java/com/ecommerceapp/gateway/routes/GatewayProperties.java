package com.ecommerceapp.gateway.routes;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ConfigurationPropertiesScan
@ConfigurationProperties(prefix = "spring.cloud.gateway")
@Data
public class GatewayProperties {
    private final String prefix;
    private final List<String> openApis;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Routes {
        private String authService;
        private String usersService;
        private String shopsService;
        private String productsService;
        private String ordersService;
        private String shipmentService;
        private String paymentService;
    }

    private final Routes routes;
}
