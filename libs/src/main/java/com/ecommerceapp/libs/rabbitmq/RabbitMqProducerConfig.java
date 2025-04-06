package com.ecommerceapp.libs.rabbitmq;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableConfigurationProperties(RabbitMqProperties.class)
@RequiredArgsConstructor
public class RabbitMqProducerConfig {
    private final RabbitMqProperties properties;

    @Bean
    public RabbitTemplate rabbitTemplate() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(properties.getHost());
        cachingConnectionFactory.setUsername(properties.getUsername());
        cachingConnectionFactory.setPassword(properties.getPassword());
        cachingConnectionFactory.setVirtualHost(properties.getVirtualHost());
        cachingConnectionFactory.setPort(properties.getPort());
        cachingConnectionFactory.setConnectionTimeout(10000);
        cachingConnectionFactory.setRequestedHeartBeat(30);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }
}
