package com.ecommerceapp.shops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@ComponentScans(value = {
        @ComponentScan("com.ecommerceapp.shops"),
        @ComponentScan("com.ecommerceapp.libs.exception"),
        @ComponentScan("com.ecommerceapp.libs.security"),
        @ComponentScan("com.ecommerceapp.libs.rest"),
        @ComponentScan("com.ecommerceapp.libs.kafka"),
        @ComponentScan("com.ecommerceapp.libs.grpc"),
        @ComponentScan("com.ecommerceapp.libs.mappers"),
        @ComponentScan("com.ecommerceapp.libs.s3")

})
public class ShopsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShopsApplication.class, args);
    }
}
