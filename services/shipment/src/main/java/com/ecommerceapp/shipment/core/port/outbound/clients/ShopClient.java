package com.ecommerceapp.shipment.core.port.outbound.clients;

import java.util.Optional;

import com.ecommerceapp.shipment.core.domain.entities.Address;

public interface ShopClient {
    Optional<Address> findShopAddressById(String id);
}
