package com.ecommerceapp.orders.core.port.outbound.clients;

import com.ecommerceapp.orders.core.domain.entities.Shop;

public interface ShopClient {
    Shop findShopById(String id);
}
