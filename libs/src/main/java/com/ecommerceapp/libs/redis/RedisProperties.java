package com.ecommerceapp.libs.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import lombok.Data;

@ConfigurationProperties(prefix = "spring.data.redis")
@ConfigurationPropertiesScan
@Data
public class RedisProperties {
    private String host;
    private Integer port;
    private String password;
    private String username;

}