package com.ecommerceapp.shops.core.domain.entities;

import java.time.Instant;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document("shops")
@Data
@NoArgsConstructor
public class Shop {
    @Id
    private ObjectId id;

    private String ownerId;

    private String name;

    private String description;

    private Integer numOfProducts;

    private Integer numOfFollower;

    private Float ratingAvg;

    private Instant createdAt;

    private Instant updatedAt;

    public Shop(
            String ownerId,
            String name,
            String description) {
        this.id = new ObjectId();
        this.ownerId = ownerId;
        this.name = name;
        this.description = description;
        this.numOfFollower = 0;
        this.numOfProducts = 0;
        this.ratingAvg = 0F;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public void addProducts(Integer numOfProduct) {
        this.numOfProducts += numOfProduct;
    }

    public void deleteProducts(Integer numOfProduct) {
        this.numOfProducts = Math.max(0, this.numOfProducts - numOfProduct);
    }

    public void addFollower() {
        this.numOfFollower++;
    }

    public void deleteFollower() {
        this.numOfFollower = Math.max(0, this.numOfFollower - 1);
    }

}
