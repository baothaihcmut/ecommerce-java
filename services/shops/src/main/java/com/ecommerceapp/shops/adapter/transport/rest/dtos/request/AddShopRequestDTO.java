package com.ecommerceapp.shops.adapter.transport.rest.dtos.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record AddShopRequestDTO(
        @NotEmpty(message = "name is required") String name,

        @NotNull(message = "description is required") String description,

        @NotEmpty(message = "shopAddress is required") String shopAddress,

        @NotEmpty(message = "shopStreet is required") String shopStreet,

        @NotEmpty(message = "shopWard is required") String shopWard,

        @NotEmpty(message = "shopDistrict is required") String shopDistrict,

        @NotEmpty(message = "shopProvince is required") String shopProvince) {
}
