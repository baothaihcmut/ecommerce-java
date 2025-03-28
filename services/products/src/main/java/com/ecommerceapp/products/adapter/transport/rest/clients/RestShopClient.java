package com.ecommerceapp.products.adapter.transport.rest.clients;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecommerceapp.products.core.domain.entities.Shop;
import com.ecommerceapp.products.core.port.outbound.clients.ShopClient;

@Service
public class RestShopClient implements ShopClient {
    private List<Shop> shops = List.of(
            new Shop("1", "bee27eb3-4fb7-4905-a4a8-496b95126c78"),
            new Shop("2", "bee27eb3-4fb7-4905-a4a8-496b95126c79"));

    @Override
    public Optional<Shop> findShopById(String shopId) {
        return shops.stream().filter(shop -> shop.getShopId().equals(shopId)).findFirst();
    }

}
