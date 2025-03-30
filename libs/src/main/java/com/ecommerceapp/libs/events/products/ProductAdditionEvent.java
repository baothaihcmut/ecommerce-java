package com.ecommerceapp.libs.events.products;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductAdditionEvent {
    private String id;

    private String name;

    private String description;

    private List<String> images;

    private String thumbnail;

    private List<String> categoryIds;

    private String shopId;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Variation {
        private String id;
        private String name;
    }

    private List<Variation> variations;

    private int soldTotal;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Instant createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Instant updatedAt;

}
