package com.ecommerceapp.products.adapter.transport.rest.dtos.response;

import java.util.List;

import com.ecommerceapp.libs.response.PresignUrlInfoResponse;

public record AddProductItemResponseDTO(
        ProductItemResponseDTO productItem,
        List<PresignUrlInfoResponse> imageUploadUrlInfos) {

}
