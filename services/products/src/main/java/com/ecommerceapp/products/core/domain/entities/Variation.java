package com.ecommerceapp.products.core.domain.entities;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Variation {
    @Field("_id")
    @Indexed(unique = true)
    private ObjectId id;

    private String name;

    public Variation(String name){
        this.id = new ObjectId();
        this.name = name;
    }

}
