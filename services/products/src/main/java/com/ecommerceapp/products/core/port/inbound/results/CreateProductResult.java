package com.ecommerceapp.products.core.port.inbound.results;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateProductResult {
    ProductResult product;

    private UploadImageResult uploadThumbnailInfo;

    private List<UploadImageResult> uploadImageInfo;
}
