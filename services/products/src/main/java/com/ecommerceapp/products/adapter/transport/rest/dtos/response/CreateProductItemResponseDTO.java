package com.ecommerceapp.products.adapter.transport.rest.dtos.response;

import java.util.List;

import com.ecommerceapp.products.core.port.inbound.results.UploadImageResult;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateProductItemResponseDTO {
    ProductItemResponseDTO productItem;

    private List<UploadImageResult> uploadImagesInfo;
}
