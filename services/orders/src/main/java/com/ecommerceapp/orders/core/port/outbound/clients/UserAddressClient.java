package com.ecommerceapp.orders.core.port.outbound.clients;

import java.util.Optional;

import com.ecommerceapp.orders.core.domain.entities.Address;

public interface UserAddressClient {
    Optional<Address> findAddressById(String id);
}
