package com.ecommerceapp.libs.rabbitmq;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import lombok.Data;

@Data
@ConfigurationPropertiesScan
@ConfigurationProperties(prefix = "spring.rabbitmq")
public class RabbitMqProperties {
    private String uri;
    private String host;
    private Integer port;
    private String username;
    private String password;
    private String virtualHost;
    private Integer concurrentConsumers;
}
