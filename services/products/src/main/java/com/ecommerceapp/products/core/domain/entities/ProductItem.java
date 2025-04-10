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
        this.variationValues = values;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public void increaseStock(Integer quantity) {
        this.quantity += quantity;
    }

    public void decreaseStock(Integer quanty) {
        if (quanty > this.quantity) {
            throw new AppException(ErrorCode.PRODUCT_ITEM_QUANTITY_CAN_NOT_LESS_THAN_ZERO);
        }
        this.quantity -= quantity;
    }

}
