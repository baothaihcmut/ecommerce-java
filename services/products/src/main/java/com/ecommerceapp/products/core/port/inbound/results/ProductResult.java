package com.ecommerceapp.products.core.port.inbound.results;

import java.time.Instant;
import java.util.List;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Builder
public class ProductResult {
    private ObjectId id;

    private String name;
    private String description;
    private List<String> images;
    private String thumbnail;
    private List<ObjectId> categoryIds;
    private String shopId;
    private List<String> variations;
    private int soldTotal;
    private Instant createdAt;
    private Instant updatedAt;
}
