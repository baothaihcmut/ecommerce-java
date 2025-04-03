package com.ecommerceapp.shops.adapter.transport.rest.dtos.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetProductsOfShopResponseDTO {
    List<ProductWithThumbnailResponseDTO> products;
}
