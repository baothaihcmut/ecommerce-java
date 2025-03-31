package com.ecommerceapp.shops.core.port.outbound.repositories;

import java.util.Optional;

import org.bson.types.ObjectId;

import com.ecommerceapp.shops.core.domain.entities.ShopFollower;

public interface ShopFollowerRepository {
    void save(ShopFollower shopFollower);

    void delete(ShopFollower shopFollower);

    Optional<ShopFollower> findByShopIdAndUserId(ObjectId shopId, String userId);

}
