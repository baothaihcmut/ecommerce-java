package com.ecommerceapp.shops.core.port.inbound.results;

import java.time.Instant;

import com.ecommerceapp.shops.core.domain.entities.Shop;

public record ShopResult(
        String id,
        String ownerId,
        String name,
        String description,
        Integer numOfProducts,
        Integer numOfFollowers,
        Float ratingAvg,
        String shopAddress,
        String shopStreet,
        String shopWard,
        String shopDistrict,
        String shopProvince,
        Instant createdAt,
        Instant updatedAt) {
    public static ShopResult toShopResult(Shop shop) {
        return new ShopResult(
                shop.getId().toHexString(),
                shop.getOwnerId(),
                shop.getName(),
                shop.getDescription(),
                shop.getNumOfProducts(),
                shop.getNumOfFollower(),
                shop.getRatingAvg(),
                shop.getShopAddress(),
                shop.getShopStreet(),
                shop.getShopWard(),
                shop.getShopDistrict(),
                shop.getShopProvince(),
                shop.getCreatedAt(),
                shop.getUpdatedAt());
    }
}
