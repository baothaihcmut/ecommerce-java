package com.ecommerceapp.libs.dtos.shop.response;

import java.time.Instant;

import org.bson.types.ObjectId;

import com.ecommerceapp.libs.serializer.ObjectIdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopResponseDTO {

    @JsonSerialize(using = ObjectIdSerializer.class)
    private ObjectId id;

    private String ownerId;

    private String name;

    private String description;

    private Integer numOfProducts;

    private Integer numOfFollower;

    private Float ratingAvg;

    private Instant createdAt;

    private Instant updatedAt;
}
