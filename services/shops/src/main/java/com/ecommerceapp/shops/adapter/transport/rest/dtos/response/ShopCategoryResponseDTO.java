package com.ecommerceapp.shops.adapter.transport.rest.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopCategoryResponseDTO {
    private String id;
    private String name;
    private String shopId;
    private String parentShopCategoryId;
}
