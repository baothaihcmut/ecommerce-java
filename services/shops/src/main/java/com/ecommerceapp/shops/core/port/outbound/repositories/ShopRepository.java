package com.ecommerceapp.shops.core.port.outbound.repositories;

import java.util.Optional;

import org.bson.types.ObjectId;

import com.ecommerceapp.shops.core.domain.entities.Shop;

public interface ShopRepository {
    void save(Shop shop);

    Optional<Shop> findShopById(ObjectId objectId);
}
