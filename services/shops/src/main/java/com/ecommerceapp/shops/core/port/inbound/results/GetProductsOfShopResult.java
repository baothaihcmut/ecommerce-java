package com.ecommerceapp.shops.core.port.inbound.results;

import java.util.List;

import com.ecommerceapp.libs.response.PaginationResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GetProductsOfShopResult {
    private List<ProductWithThumbnailResult> products;
    private PaginationResponse pagination;
}
