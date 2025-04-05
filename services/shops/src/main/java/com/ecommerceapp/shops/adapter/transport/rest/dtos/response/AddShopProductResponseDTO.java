package com.ecommerceapp.shops.adapter.transport.rest.dtos.response;

import java.util.List;

import com.ecommerceapp.libs.response.PresignUrlInfoResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddShopProductResponseDTO {
    ProductResponseDTO product;
    PresignUrlInfoResponse thumbnailPresignUrl;
    List<PresignUrlInfoResponse> imagePresignUrls;
}
