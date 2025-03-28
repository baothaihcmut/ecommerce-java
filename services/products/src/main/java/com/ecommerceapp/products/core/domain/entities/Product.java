package com.ecommerceapp.products.core.domain.entities;

import java.time.Instant;
import java.util.List;
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

    private List<String> variations;

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
        if (!checkDuplicateVariation(variations)) {
            throw new AppException(ErrorCode.DUPLICATE_PRODUCT_VARIATION);
        }
        this.variations = variations;
        this.soldTotal = 0;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    private boolean checkDuplicateVariation(List<String> variations) {
        return variations.stream()
                .distinct().count() == variations.size();
    }

    public void addVariations(String variation) {
        this.variations.add(variation);
        if (!checkDuplicateVariation(variations)) {
            throw new AppException(ErrorCode.DUPLICATE_PRODUCT_VARIATION);
        }
    }

}
