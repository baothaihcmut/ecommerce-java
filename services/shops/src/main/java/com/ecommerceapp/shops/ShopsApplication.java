package com.ecommerceapp.shops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@ComponentScans(value = {
        @ComponentScan("com.ecommerceapp.shops"),
        @ComponentScan("com.ecommerceapp.libs"),
})
public class ShopsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShopsApplication.class, args);
    }
}
