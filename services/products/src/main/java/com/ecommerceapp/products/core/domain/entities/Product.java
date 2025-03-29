package com.ecommerceapp.products.core.domain.entities;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.ecommerceapp.libs.exception.AppException;
import com.ecommerceapp.products.core.exception.ErrorCode;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "products")
@Data
@NoArgsConstructor
public class Product {
    @Id
    private ObjectId id;

    private String name;

    private String description;

    private List<String> images;

    private String thumbnail;

    private List<ObjectId> categoryIds;

    private String shopId;

    private List<Variation> variations;

    private int soldTotal;

    private Instant createdAt;

    private Instant updatedAt;

    public Product(
            String name,
            String description,
            Boolean hasThumbnail,
            Integer numOfImage,
            List<ObjectId> categroyIds,
            String shopId,
            List<String> variations) {
        this.id = new ObjectId();
        this.name = name;
        this.description = description;
        // generate randome id for thumbnail image
        if (hasThumbnail) {
            this.thumbnail = UUID.randomUUID().toString();
        }
        this.images = IntStream.range(0, numOfImage)
                .mapToObj(i -> UUID.randomUUID().toString()).toList();
        this.categoryIds = categroyIds;
        this.shopId = shopId;

        this.variations = variations.stream().map(variation -> new Variation(variation)).toList();
        this.soldTotal = 0;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public void addVariations(String variation) {
        this.variations.add(new Variation(variation));
    }

    public void checkVarions(List<VariationValue> values) {
        Optional<Variation> notExist = variations.stream()
                .filter(variation -> !values.stream()
                        .anyMatch(value -> value.getVariationId().equals(variation.getId())))
                .findFirst();
        if (notExist.isPresent()) {
            throw new AppException(ErrorCode.VARIATION_NOT_EXIST_IN_PRODUCT.withDetails(
                    Map.of("variationId", notExist.get().getId())));
        }
    }

}
