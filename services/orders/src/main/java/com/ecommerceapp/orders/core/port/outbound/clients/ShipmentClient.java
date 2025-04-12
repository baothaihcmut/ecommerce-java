package com.ecommerceapp.orders.core.port.outbound.clients;

import java.util.List;

import com.ecommerceapp.orders.core.domain.entities.Address;
import com.ecommerceapp.orders.core.domain.enums.ShipProvider;

public interface ShipmentClient {
    public record GetShippingFeeArg(
            Address address,
            ShipProvider shipProvider,
            List<String> productItemIds) {
    }

    Integer getShippingFee(GetShippingFeeArg arg);
}
