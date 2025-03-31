package com.ecommerceapp.shops.core.port.inbound.results;

import java.util.List;

import com.ecommerceapp.libs.response.PresignUrlInfoResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CreateProductResult {
    ProductResult product;
    PresignUrlInfoResponse thumbnailPresignUrl;
    List<PresignUrlInfoResponse> imagePresignUrls;
}
