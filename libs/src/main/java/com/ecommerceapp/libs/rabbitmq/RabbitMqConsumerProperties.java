package com.ecommerceapp.libs.rabbitmq;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import lombok.Data;

@Data
@ConfigurationPropertiesScan
@ConfigurationProperties(prefix = "spring.rabbitmq.consumer")
public class RabbitMqConsumerProperties {
    private String host;
    private Integer port;
    private String username;
    private String password;
    private String vhost;
    private Integer concurrentConsumers;
}
