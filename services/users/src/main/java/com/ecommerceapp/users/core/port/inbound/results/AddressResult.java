package com.ecommerceapp.users.core.port.inbound.results;

import com.ecommerceapp.users.core.domain.entities.Address;

public record AddressResult(
        String id,
        String userId,
        String street,
        String town,
        String city,
        String province) {
    public static AddressResult fromAddressDomain(Address address) {
        return new AddressResult(
                address.getId().toString(),
                address.getUser().getId().toString(),
                address.getStreet(),
                address.getTown(),
                address.getCity(),
                address.getProvince());
    }
}
