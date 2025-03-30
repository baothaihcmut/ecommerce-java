package com.ecommerceapp.products.adapter.transport.rest.dtos.response;

import java.time.Instant;
import java.util.List;

import org.bson.types.ObjectId;

import com.ecommerceapp.libs.serializer.ObjectIdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductItemResponseDTO {
    @JsonSerialize(using = ObjectIdSerializer.class)
    private ObjectId id;

    @JsonSerialize(using = ObjectIdSerializer.class)
    private ObjectId productId;

    private int quantity;

    private int price;

    private List<String> images;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class VarationValueResponseDTO {
        @JsonSerialize(using = ObjectIdSerializer.class)
        private ObjectId variationId;

        private String value;
    }

    private List<VarationValueResponseDTO> variationValues;

    private Instant createdAt;

    private Instant updatedAt;
}
