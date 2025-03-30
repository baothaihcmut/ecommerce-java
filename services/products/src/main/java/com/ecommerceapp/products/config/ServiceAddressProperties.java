package com.ecommerceapp.products.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import lombok.Data;

@ConfigurationProperties(prefix = "services.address")
@ConfigurationPropertiesScan
@Data
public class ServiceAddressProperties {
    private String shopService;
}
