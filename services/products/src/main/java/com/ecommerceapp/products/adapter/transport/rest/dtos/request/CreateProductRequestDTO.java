package com.ecommerceapp.products.adapter.transport.rest.dtos.request;

import java.util.List;

import org.bson.types.ObjectId;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateProductRequestDTO {

    @NotEmpty(message = "name is required")
    private String name;

    @NotNull(message = "description field must not be null")
    private String description;

    @NotNull(message = "num of image is required")
    @Min(0)
    private Integer numOfImage;

    @NotNull(message = "has thumbnail is required")
    private Boolean hasThumbnail;

    @NotNull(message = "category ids field must not be null")
    @Valid
    private List<ObjectId> categoryIds;

    @NotEmpty(message = "shop id is required")
    private String shopId;

    @NotNull(message = "variations field must not be null")
    private List<String> variations;
}
