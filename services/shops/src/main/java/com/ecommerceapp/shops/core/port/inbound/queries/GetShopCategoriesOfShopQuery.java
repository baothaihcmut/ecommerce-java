package com.ecommerceapp.shops.core.port.inbound.queries;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class GetShopCategoriesOfShopQuery {
    private String shopId;
}
