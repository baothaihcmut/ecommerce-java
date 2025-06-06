package com.ecommerceapp.shops.adapter.transport.rest.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopFollowerResponseDTO {
    private String shopId;
    private String userId;
}
