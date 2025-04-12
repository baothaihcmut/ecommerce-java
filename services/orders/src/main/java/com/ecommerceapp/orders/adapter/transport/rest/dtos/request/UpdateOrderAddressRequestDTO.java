package com.ecommerceapp.orders.adapter.transport.rest.dtos.request;

import com.ecommerceapp.orders.core.domain.enums.ShipProvider;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UpdateOrderAddressRequestDTO(
        @NotEmpty(message = "address id is required") String addressId,
        @NotNull(message = "provider is required") ShipProvider shipProvider) {

}
