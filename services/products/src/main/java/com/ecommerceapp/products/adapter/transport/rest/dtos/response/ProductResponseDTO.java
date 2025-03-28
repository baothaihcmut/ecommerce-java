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
public class ProductResponseDTO {
    @JsonSerialize(using = ObjectIdSerializer.class)
    private ObjectId id;

    private String name;

    private String description;

    private List<String> images;

    private String thumbnail;

    @JsonSerialize(contentUsing = ObjectIdSerializer.class)
    private List<ObjectId> categoryIds;

    private String shopId;

    private List<String> variations;

    private int soldTotal;

    private Instant createdAt;

    private Instant updatedAt;
}
