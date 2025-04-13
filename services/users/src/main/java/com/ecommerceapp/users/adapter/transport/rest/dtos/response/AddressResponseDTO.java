package com.ecommerceapp.users.adapter.transport.rest.dtos.response;

public record AddressResponseDTO(
        String id,
        String userId,
        String address,
        String street,
        String ward,
        String district,
        String province) {

}
