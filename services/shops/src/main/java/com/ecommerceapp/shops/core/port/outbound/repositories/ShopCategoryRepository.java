package com.ecommerceapp.shops.core.port.outbound.repositories;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;

import com.ecommerceapp.shops.core.domain.entities.ShopCategory;

public interface ShopCategoryRepository {
    void save(ShopCategory shopCategory);

    Optional<ShopCategory> findShopCategoryById(ObjectId id);

    List<ShopCategory> findShopCategoryByShopId(ObjectId shopId);
}
