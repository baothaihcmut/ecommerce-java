package com.ecommerceapp.shops.core.domain.entities;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("shop_categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopCategory {
    private ObjectId id;
    private String name;
    private ObjectId shopId;
    private ObjectId parentShopCategoryId;

    public ShopCategory(
            String name,
            ObjectId paretnShopCategoryId,
            ObjectId shopId) {
        this.id = new ObjectId();
        this.name = name;
        this.shopId = shopId;
        this.parentShopCategoryId = paretnShopCategoryId;
    }
}
