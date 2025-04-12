package com.ecommerceapp.orders.core.port.inbound.commands;

import com.ecommerceapp.orders.core.domain.enums.ShipProvider;

public record UpdateOrderAddressCommand(
        String orderId,
        String addressId,
        ShipProvider shipProvider) {

}
