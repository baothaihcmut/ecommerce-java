package com.ecommerceapp.users.adapter.external;

import org.springframework.stereotype.Service;

import com.ecommerceapp.libs.events.users.UserRegisterEvent;
import com.ecommerceapp.libs.kafka.KafkaService;
import com.ecommerceapp.users.core.port.outbound.external.EventPublisher;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventPuslisherImpl implements EventPublisher {
    private final KafkaService kafkaService;
    private static final String USER_REGISTER_TOPIC = "users.register";

    @Override
    public void publishUserRegisterEvent(UserRegisterEvent event) {
        this.kafkaService.sendMessage(USER_REGISTER_TOPIC, null, event);
    }

}
