package com.ecommerceapp.mail.services;

import org.springframework.stereotype.Service;

import com.ecommerceapp.libs.events.users.UserRegisterEvent;
import com.ecommerceapp.libs.mailer.MailerService;
import com.ecommerceapp.mail.models.UserRegister;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final MailerService mailerService;

    public void sendMailUserRegister(UserRegisterEvent event) throws Exception {
        UserRegister userRegister = UserRegister.builder()
                .email(event.getEmail())
                .firstName(event.getFirstName())
                .lastName(event.getLastName())
                .confirmUrl(event.getComfirmUrl())
                .build();
        this.mailerService.sendEmail(event.getEmail(), "Register confirm", "confirm-register-template", userRegister);
    }
}
