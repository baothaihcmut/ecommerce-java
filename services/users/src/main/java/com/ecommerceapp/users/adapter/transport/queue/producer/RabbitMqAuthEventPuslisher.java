package com.ecommerceapp.users.adapter.transport.queue.producer;

import org.springframework.stereotype.Service;

import com.ecommerceapp.libs.events.users.UserRegisterEvent;
import com.ecommerceapp.libs.rabbitmq.RabbitMqService;
import com.ecommerceapp.users.core.events.AuthEventPublisher;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RabbitMqAuthEventPuslisher implements AuthEventPublisher {
    private final RabbitMqService rabbitMqService;
    private static final String USER_EVENTS_EXCHANGE = "user-events";
    private static final String USER_REGISTER_ROUTING_KEY = "user.signup";

    @Override
    public void publishUserRegisterEvent(UserRegisterEvent event) {
        rabbitMqService.sendMessage(USER_EVENTS_EXCHANGE, USER_REGISTER_ROUTING_KEY, event, null);
    }

}
