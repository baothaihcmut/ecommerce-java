package com.ecommerceapp.shops.adapter.transport.rest.dtos.response;

import java.util.List;

import com.ecommerceapp.libs.response.PaginationResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetFollowersOfShopResponseDTO {
    private List<UserResponseDTO> followers;
    private PaginationResponse pagination;
}
