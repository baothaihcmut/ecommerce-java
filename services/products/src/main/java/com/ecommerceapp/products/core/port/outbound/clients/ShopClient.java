package com.ecommerceapp.products.core.port.outbound.clients;

import java.util.Optional;

import com.ecommerceapp.products.core.domain.entities.Shop;

public interface ShopClient {
    Optional<Shop> findShopById(String shopId);
}
