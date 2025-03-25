package com.ecommerceapp.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@ComponentScans({
    @ComponentScan("com.ecommerceapp.users"),
    @ComponentScan("com.ecommerceapp.libs.exception"),
    @ComponentScan("com.ecommerceapp.libs.redis")
})
public class UsersApplication {
     public static void main(String[] args) {
        SpringApplication.run(UsersApplication.class, args);
    }
}
