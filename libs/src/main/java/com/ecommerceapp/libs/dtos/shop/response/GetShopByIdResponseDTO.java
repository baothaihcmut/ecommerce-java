package com.ecommerceapp.libs.dtos.shop.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetShopByIdResponseDTO {
    private ShopResponseDTO shop;
}
