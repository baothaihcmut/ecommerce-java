package com.ecommerceapp.products;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@ComponentScans(value = {
        @ComponentScan("com.ecommerceapp.products"),
        @ComponentScan("com.ecommerceapp.libs.exception"),
        @ComponentScan("com.ecommerceapp.libs.interceptors"),
        @ComponentScan("com.ecommerceapp.libs.s3")
})
public class ProductsApplication {
    public static void main(String[] args) {
        
        SpringApplication.run(ProductsApplication.class, args);
    }
}
