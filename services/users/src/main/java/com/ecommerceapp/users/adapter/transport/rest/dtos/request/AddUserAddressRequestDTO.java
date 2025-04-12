package com.ecommerceapp.users.adapter.transport.rest.dtos.request;

import jakarta.validation.constraints.NotEmpty;

public record AddUserAddressRequestDTO(
        @NotEmpty(message = "street is required") String street,
        @NotEmpty(message = "town is required") String town,
        @NotEmpty(message = "city is required") String city,
        @NotEmpty(message = "province is required") String province) {

}
