package com.ecommerceapp.users.core.services;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import lombok.Data;

@ConfigurationProperties(prefix = "jwt")
@ConfigurationPropertiesScan
@Data
public class JwtProperties {

    @Data
    public static class TokenProperty {

        private String secret;
        private Integer expiry;
    }

    private TokenProperty accessToken;
    private TokenProperty refreshToken;
}