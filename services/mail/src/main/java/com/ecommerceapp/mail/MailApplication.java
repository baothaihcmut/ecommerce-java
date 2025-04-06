package com.ecommerceapp.mail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@ComponentScans({
        @ComponentScan("com.ecommerceapp.mail"),

        @ComponentScan("com.ecommerceapp.libs.mailer"),
        @ComponentScan("com.ecommerceapp.libs.rabbitmq"),
})
public class MailApplication {
    public final static String CONSUMER_GROUP_ID = "mail-service";

    public static void main(String[] args) {
        SpringApplication.run(MailApplication.class, args);
    }
}
