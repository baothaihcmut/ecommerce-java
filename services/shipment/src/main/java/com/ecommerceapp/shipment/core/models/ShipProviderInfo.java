package com.ecommerceapp.shipment.core.models;

import com.ecommerceapp.shipment.core.domain.entities.Address;

public record ShipProviderInfo(
        String pickAddress,
        String pickStreet,
        String pickWard,
        String pickDistrict,
        String pickProvince,
        String address,
        String street,
        String ward,
        String district,
        String province,
        Integer weight,
        String deliveryOption) {
    public static ShipProviderInfo toShipProviderInfo(
            Integer weight,
            Address recieveAddress,
            Address shopAddress) {
        return new ShipProviderInfo(
                shopAddress.getAddress(),
                shopAddress.getStreet(),
                shopAddress.getWard(),
                shopAddress.getDistrict(),
                shopAddress.getProvince(),
                recieveAddress.getAddress(),
                recieveAddress.getStreet(),
                recieveAddress.getWard(),
                recieveAddress.getDistrict(),
                recieveAddress.getProvince(),
                weight,
                "none");

    }
}
