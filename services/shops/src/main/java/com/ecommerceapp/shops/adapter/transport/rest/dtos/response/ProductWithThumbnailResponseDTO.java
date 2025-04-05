package com.ecommerceapp.shops.adapter.transport.rest.dtos.response;

import com.ecommerceapp.libs.response.PresignUrlInfoResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductWithThumbnailResponseDTO {
    ProductResponseDTO product;
    PresignUrlInfoResponse thumbnailPresignUrlInfo;
}
