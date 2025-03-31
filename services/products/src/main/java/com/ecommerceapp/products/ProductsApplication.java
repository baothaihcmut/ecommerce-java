package com.ecommerceapp.products;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.FilterType;

import com.ecommerceapp.libs.kafka.KafkaConsumerConfig;
import com.ecommerceapp.products.config.ServiceAddressProperties;

@SpringBootApplication
@ComponentScans(value = {
                @ComponentScan("com.ecommerceapp.products"),
                @ComponentScan("com.ecommerceapp.libs.exception"),
                @ComponentScan("com.ecommerceapp.libs.security"),
                @ComponentScan("com.ecommerceapp.libs.rest"),
                @ComponentScan(value = "com.ecommerceapp.libs.kafka", excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = KafkaConsumerConfig.class)),
})
@EnableConfigurationProperties(ServiceAddressProperties.class)
public class ProductsApplication {
        public static void main(String[] args) {

                SpringApplication.run(ProductsApplication.class, args);
        }
}
