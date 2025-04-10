package com.ecommerceapp.products;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

import com.ecommerceapp.products.config.ServiceAddressProperties;

@SpringBootApplication
@ComponentScans(value = {
                @ComponentScan("com.ecommerceapp.products"),
                @ComponentScan("com.ecommerceapp.libs"),
})
@EnableConfigurationProperties(ServiceAddressProperties.class)
public class ProductsApplication {
        public static void main(String[] args) {

                SpringApplication.run(ProductsApplication.class, args);
        }
}
