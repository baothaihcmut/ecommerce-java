package com.ecommerceapp.shipment.core.port.inbound.queries;

import java.util.List;

import com.ecommerceapp.shipment.core.domain.enums.ShipProvider;

public record GetShipmentFeeQuery(
                List<ShipItem> shipItems,
                String shopId,
                ShipProvider shipProvider,
                String recieveAddress,
                String recieveStreet,
                String recieveWard,
                String recieveDistrict,
                String recieveProvince) {

        public record ShipItem(
                        String productItemId,
                        Integer quantity) {
        }

}
