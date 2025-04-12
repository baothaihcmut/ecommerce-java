package com.ecommerceapp.users.core.port.outbound.repositories;

import java.util.Optional;
import java.util.UUID;

import com.ecommerceapp.users.core.domain.entities.Address;

public interface AddressRepository {
    void save(Address address);

    Optional<Address> findAddressById(UUID id);
}
