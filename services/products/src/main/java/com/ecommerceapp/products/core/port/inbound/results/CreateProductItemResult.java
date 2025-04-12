package com.ecommerceapp.products.core.port.inbound.results;

import java.util.List;

import com.ecommerceapp.libs.response.PresignUrlInfoResponse;

public record CreateProductItemResult(
        ProductItemResult productItem,
        List<PresignUrlInfoResponse> imageUploadUrlInfos) {
}
