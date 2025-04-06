package com.ecommerceapp.libs.rabbitmq;

import java.util.Map;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RabbitMqService {
    private final RabbitTemplate rabbitTemplate;

    public <T> void sendMessage(String exchange, String routingKey, T message, Map<String, String> headers) {
        if (headers != null) {
            rabbitTemplate.convertAndSend(exchange, routingKey, message, new MessagePostProcessor() {
                @Override
                public Message postProcessMessage(Message message) {
                    headers.forEach((key, value) -> message.getMessageProperties().setHeader(key, value));
                    return message;
                }
            });
        } else {
            rabbitTemplate.convertAndSend(exchange, routingKey, message);
        }

    }
}
