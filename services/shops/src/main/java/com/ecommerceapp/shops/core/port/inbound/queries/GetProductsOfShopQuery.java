package com.ecommerceapp.shops.core.port.inbound.queries;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetProductsOfShopQuery {
    private String shopId;
    private Boolean hasThumbnail;
    private Integer offset;
    private Integer limit;
}
