package com.ecommerceapp.products.adapter.db.mongo.seeders;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductSeeder {
    // private final MongoTemplate mongoTemplate;

    @PostConstruct
    public void runProductSeeder() {

    }
}
