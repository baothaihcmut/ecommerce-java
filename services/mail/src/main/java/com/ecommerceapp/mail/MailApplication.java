package com.ecommerceapp.mail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

import com.ecommerceapp.libs.rabbitmq.RabbitMqConsumerConfig;

@SpringBootApplication
@ComponentScans({
        @ComponentScan("com.ecommerceapp.mail"),
        @ComponentScan("com.ecommerceapp.libs.mailer"),

        @ComponentScan(basePackageClasses = RabbitMqConsumerConfig.class),
})
public class MailApplication {
    public final static String CONSUMER_GROUP_ID = "mail-service";

    public static void main(String[] args) {
        SpringApplication.run(MailApplication.class, args);
    }
}
