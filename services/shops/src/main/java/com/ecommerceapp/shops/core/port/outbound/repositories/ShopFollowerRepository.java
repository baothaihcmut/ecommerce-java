package com.ecommerceapp.shops.core.port.outbound.repositories;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;

import com.ecommerceapp.shops.core.domain.entities.ShopFollower;

public interface ShopFollowerRepository {
    void save(ShopFollower shopFollower);

    void delete(ShopFollower shopFollower);

    Optional<ShopFollower> findByShopIdAndUserId(ObjectId shopId, String userId);

    public record FindShopFollowersByShopIdAndCountResult(List<ShopFollower> followers, long count) {
    }

    FindShopFollowersByShopIdAndCountResult findShopFollowersByShopIdAndCount(ObjectId shopId, Integer limit,
            Integer offset);

}
