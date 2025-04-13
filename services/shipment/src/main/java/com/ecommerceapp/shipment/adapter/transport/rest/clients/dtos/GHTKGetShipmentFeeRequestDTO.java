package com.ecommerceapp.shipment.adapter.transport.rest.clients.dtos;

import com.ecommerceapp.shipment.core.models.ShipProviderInfo;

public record GHTKGetShipmentFeeRequestDTO(
        String pick_address,
        String pick_street,
        String pick_ward,
        String pick_district,
        String pick_province,
        String address,
        String street,
        String ward,
        String district,
        String province,
        Integer weight,
        String delivery_option) {
    public static GHTKGetShipmentFeeRequestDTO fromShipProviderInfo(ShipProviderInfo shipProviderInfo) {
        return new GHTKGetShipmentFeeRequestDTO(
                shipProviderInfo.pickAddress(),
                shipProviderInfo.pickStreet(),
                shipProviderInfo.pickWard(),
                shipProviderInfo.pickDistrict(),
                shipProviderInfo.pickProvince(),
                shipProviderInfo.address(),
                shipProviderInfo.street(),
                shipProviderInfo.ward(),
                shipProviderInfo.district(),
                shipProviderInfo.province(),
                shipProviderInfo.weight(),
                shipProviderInfo.deliveryOption());
    }

}
