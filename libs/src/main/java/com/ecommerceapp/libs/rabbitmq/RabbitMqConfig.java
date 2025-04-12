package com.ecommerceapp.libs.rabbitmq;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableConfigurationProperties(RabbitMqProperties.class)
@ConditionalOnProperty(name = "spring.rabbitmq.enabled", havingValue = "true", matchIfMissing = false)
@RequiredArgsConstructor
public class RabbitMqConfig {
    private final RabbitMqProperties properties;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(properties.getHost());
        if (properties.getUri() != null) {
            cachingConnectionFactory.setUri(properties.getUri());
        } else {
            cachingConnectionFactory.setUsername(properties.getUsername());
            cachingConnectionFactory.setPassword(properties.getPassword());
            cachingConnectionFactory.setVirtualHost(properties.getVirtualHost());
            cachingConnectionFactory.setPort(properties.getPort());
        }
        cachingConnectionFactory.setConnectionTimeout(10000);
        cachingConnectionFactory.setRequestedHeartBeat(30);
        return cachingConnectionFactory;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setConcurrentConsumers(properties.getConcurrentConsumers());
        factory.setConnectionFactory(connectionFactory);
        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }
}
