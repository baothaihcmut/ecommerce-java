package com.ecommerceapp.shops.adapter.transport.rest.dtos.response;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopResponseDTO {

    private String id;

    private String ownerId;

    private String name;

    private String description;

    private Integer numOfProducts;

    private Integer numOfFollowers;

    private Float ratingAvg;

    private Instant createdAt;

    private Instant updatedAt;
}
