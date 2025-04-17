package com.ecommerceapp.mail.handlers;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.ecommerceapp.libs.events.users.UserRegisterEvent;
import com.ecommerceapp.mail.services.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserEventHandler {
    private final UserService userService;

    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "mail-users-signup", durable = "true"), exchange = @Exchange(value = "user-events", ignoreDeclarationExceptions = "true"), key = "user.signup"))
    public void handleUserRegisterEvent(UserRegisterEvent event) throws Exception {
        log.info("Handle user sign up event");
        this.userService.sendMailUserRegister(event);

    }

}
