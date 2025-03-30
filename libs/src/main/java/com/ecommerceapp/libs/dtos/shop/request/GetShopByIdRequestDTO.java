package com.ecommerceapp.libs.dtos.shop.request;

import org.bson.types.ObjectId;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetShopByIdRequestDTO {
    @NotNull(message = "shop id is required")
    private ObjectId shopId;
}
