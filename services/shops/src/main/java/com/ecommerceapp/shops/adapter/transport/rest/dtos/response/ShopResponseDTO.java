package com.ecommerceapp.shops.adapter.transport.rest.dtos.response;

import java.time.Instant;

public record ShopResponseDTO(
        String id,
        String ownerId,
        String name,
        String description,
        Integer numOfProducts,
        Integer numOfFollowers,
        Float ratingAvg,
        Instant createdAt,
        Instant updatedAt,
        String shopAddress,
        String shopStreet,
        String shopWard,
        String shopDistrict,
        String shopProvince) {
}