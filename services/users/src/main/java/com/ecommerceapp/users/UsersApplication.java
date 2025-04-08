package com.ecommerceapp.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.FilterType;

import com.ecommerceapp.libs.rabbitmq.RabbitMqConfig;

@SpringBootApplication
@ComponentScans({
        @ComponentScan("com.ecommerceapp.users"),
        @ComponentScan("com.ecommerceapp.libs.exception"),
        @ComponentScan("com.ecommerceapp.libs.redis"),
        @ComponentScan("com.ecommerceapp.libs.grpc"),
        @ComponentScan("com.ecommerceapp.libs.security"),
        @ComponentScan(basePackages = "com.ecommerceapp.libs.rabbitmq", excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = RabbitMqConfig.class)),
        @ComponentScan("com.ecommerceapp.libs.docs")

})

public class UsersApplication {
    public static void main(String[] args) {
        SpringApplication.run(UsersApplication.class, args);
    }
}
