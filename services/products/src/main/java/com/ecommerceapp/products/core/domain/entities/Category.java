package com.ecommerceapp.products.core.domain.entities;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "categories")
@Data
@NoArgsConstructor
public class Category {

    @Id
    private ObjectId id;

    private String name;

    public Category(String name) {
        this.id = new ObjectId();
        this.name = name;
    }
}
