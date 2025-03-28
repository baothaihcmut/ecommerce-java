package com.ecommerceapp.products.core.domain.entities;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "product_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductItem {
    @Id
    private ObjectId id;

    private ObjectId productId;

    private int quantity;

    private int price;

    private List<String> images;

    private List<VariationValue> variationValues;

    private Instant createdAt;

    private Instant updatedAt;

    public ProductItem(
            ObjectId productId,
            Integer quantity,
            Integer price,
            Integer numOfImage,
            List<VariationValue> values) {
        this.id = new ObjectId();
        this.productId = productId;
        this.quantity = quantity < 0 ? 0 : quantity;
        this.price = price < 0 ? 0 : price;
        this.images = IntStream.range(0, numOfImage)
                .mapToObj(i -> UUID.randomUUID().toString()).toList();
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

}
