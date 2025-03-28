package com.ecommerceapp.users.adapter.transport.queue.producer;

import org.springframework.stereotype.Service;

import com.ecommerceapp.libs.events.users.UserRegisterEvent;
import com.ecommerceapp.libs.kafka.KafkaService;
import com.ecommerceapp.users.core.events.AuthEventPublisher;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthEventPuslisherImpl implements AuthEventPublisher {
    private final KafkaService kafkaService;
    private static final String USER_REGISTER_TOPIC = "users.register";

    @Override
    public void publishUserRegisterEvent(UserRegisterEvent event) {
        this.kafkaService.sendMessage(USER_REGISTER_TOPIC, null, event);
    }

}
