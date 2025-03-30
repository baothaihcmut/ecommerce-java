package com.ecommerceapp.shops.core.port.inbound.results;

import java.time.Instant;

import com.ecommerceapp.shops.core.domain.entities.Shop;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShopResult {
    private String id;

    private String ownerId;

    private String name;

    private String description;

    private Integer numOfProducts;

    private Integer numOfFollowers;

    private Float ratingAvg;

    private Instant createdAt;

    private Instant updatedAt;

    public static ShopResult toShopResult(Shop shop) {
        return ShopResult.builder()
                .id(shop.getId().toHexString())
                .name(shop.getName())
                .description(shop.getDescription())
                .ownerId(shop.getOwnerId())
                .numOfFollowers(shop.getNumOfFollower())
                .numOfProducts(shop.getNumOfProducts())
                .ratingAvg(shop.getRatingAvg())
                .createdAt(shop.getCreatedAt())
                .updatedAt(shop.getUpdatedAt())
                .build();
    }

}
