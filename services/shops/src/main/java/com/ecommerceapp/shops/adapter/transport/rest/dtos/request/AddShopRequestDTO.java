package com.ecommerceapp.shops.adapter.transport.rest.dtos.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddShopRequestDTO {
    @NotEmpty(message = "name is required")
    private String name;

    @NotNull(message = "description is required")
    private String description;
}
