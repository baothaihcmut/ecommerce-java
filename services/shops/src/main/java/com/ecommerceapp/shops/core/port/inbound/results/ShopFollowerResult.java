package com.ecommerceapp.shops.core.port.inbound.results;

import java.time.Instant;

import com.ecommerceapp.shops.core.domain.entities.ShopFollower;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShopFollowerResult {
    private String shopId;
    private String userId;
    private Instant createdAt;

    public static ShopFollowerResult toSopFollowerResult(ShopFollower shopFollower) {
        return ShopFollowerResult.builder()
                .shopId(shopFollower.getShopId().toHexString())
                .userId(shopFollower.getUserId())
                .createdAt(shopFollower.getCreatedAt())
                .build();
    }
}
