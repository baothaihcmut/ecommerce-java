package com.ecommerceapp.users.core.port.inbound.results;

import com.ecommerceapp.users.core.domain.entities.Address;

public record AddressResult(
        String id,
        String userId,
        String address,
        String street,
        String ward,
        String district,
        String province) {
    public static AddressResult fromAddressDomain(Address address) {
        return new AddressResult(
                address.getId().toString(),
                address.getUser().getId().toString(),
                address.getAddress(),
                address.getStreet(),
                address.getWard(),
                address.getDistrict(),
                address.getProvince());
    }
}
