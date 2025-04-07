package com.ecommerceapp.shops.core.port.inbound.results;

import com.ecommerceapp.shops.core.domain.entities.ShopCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShopCategoryResult {
    private String id;
    private String name;
    private String parentShopCategoryId;
    private String shopId;

    public static ShopCategoryResult toShopCategoryResult(ShopCategory shopCategory) {
        return ShopCategoryResult.builder()
                .id(shopCategory.getId().toHexString())
                .name(shopCategory.getName())
                .parentShopCategoryId(shopCategory.getParentShopCategoryId() != null
                        ? shopCategory.getParentShopCategoryId().toHexString()
                        : null)
                .shopId(shopCategory.getShopId().toHexString())
                .build();
    }
}
