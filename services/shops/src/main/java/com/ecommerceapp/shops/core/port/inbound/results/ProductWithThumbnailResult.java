package com.ecommerceapp.shops.core.port.inbound.results;

import com.ecommerceapp.libs.response.PresignUrlInfoResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductWithThumbnailResult {
    ProductResult product;
    PresignUrlInfoResponse thumbnailPresignUrlInfo;
}
