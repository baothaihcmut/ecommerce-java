package com.ecommerceapp.products.adapter.transport.rest.dtos.response;

import java.util.List;

import com.ecommerceapp.products.core.port.inbound.results.UploadImageResult;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductResponseDTO {
    private ProductResponseDTO product;

    private UploadImageResult uploadThumbnailInfo;

    private List<UploadImageResult> uploadImageInfo;
}
