package com.ecommerceapp.shops.core.domain.entities;

import java.time.Instant;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@CompoundIndexes({
        @CompoundIndex(name = "unique_shop_follower", def = "{'shopId': 1, 'userId': 1}", unique = true),
})
public class ShopFollower {
    private ObjectId shopId;
    private String userId;
    private Instant createdAt;

    public ShopFollower(
            ObjectId shopId,
            String userId) {
        this.shopId = shopId;
        this.userId = userId;
        this.createdAt = Instant.now();
    }
}
