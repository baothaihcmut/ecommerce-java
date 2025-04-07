package com.ecommerceapp.shops.adapter.transport.rest.dtos.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddShopCategoryRequestDTO {
    @NotEmpty(message = "name is required")
    private String name;

}
