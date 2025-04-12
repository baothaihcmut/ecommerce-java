package com.ecommerceapp.users.adapter.transport.rest.dtos.response;

public record AddressResponseDTO(
        String id,
        String userId,
        String street,
        String town,
        String city,
        String province) {

}
