package com.ecommerceapp.shops.adapter.transport.rest.dtos.request;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AddShopProductRequestDTO {
    @NotEmpty(message = "name is required")
    private String name;

    @NotNull(message = "description field is required")
    private String description;

    @NotNull(message = "hasThumbnail field is required")
    private Boolean hasThumbnail;

    @NotNull(message = "numOfImage field is required")
    private Integer numOfImage;

    @NotNull(message = "variations field is required")
    private List<String> variations;

    @NotNull(message = "categoryIds field is required")
    private List<String> categoryIds;
}
