package com.ecommerceapp.mail.handlers;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.ecommerceapp.libs.events.users.UserRegisterEvent;
import com.ecommerceapp.mail.services.UserService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserEventHandler {
    private final UserService userService;
    private final static String USER_REGISTER_TOPIC = "users.register";

    @KafkaListener(topics = USER_REGISTER_TOPIC, groupId = "email-service", properties = {
            "spring.json.value.default.type=com.ecommerceapp.libs.events.users.UserRegisterEvent" })
    public void handleUserRegisterEvent(UserRegisterEvent event) throws Exception {
        this.userService.sendMailUserRegister(event);

    }

}
