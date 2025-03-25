package com.ecommerceapp.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.FilterType;

import com.ecommerceapp.libs.kafka.KafkaConsumerConfig;

@SpringBootApplication
@ComponentScans({
        @ComponentScan("com.ecommerceapp.users"),
        @ComponentScan("com.ecommerceapp.libs.exception"),
        @ComponentScan("com.ecommerceapp.libs.redis"),
        @ComponentScan(value = "com.ecommerceapp.libs.kafka", excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = KafkaConsumerConfig.class)),
})

public class UsersApplication {
    public static void main(String[] args) {
        SpringApplication.run(UsersApplication.class, args);
    }
}
