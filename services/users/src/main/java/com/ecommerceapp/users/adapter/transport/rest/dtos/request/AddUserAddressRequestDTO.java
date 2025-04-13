package com.ecommerceapp.users.adapter.transport.rest.dtos.request;

import jakarta.validation.constraints.NotEmpty;

public record AddUserAddressRequestDTO(
        @NotEmpty(message = "address is required") String address,
        @NotEmpty(message = "street is required") String street,
        @NotEmpty(message = "ward is required") String ward,
        @NotEmpty(message = "district is required") String district,
        @NotEmpty(message = "province is required") String province) {

}
