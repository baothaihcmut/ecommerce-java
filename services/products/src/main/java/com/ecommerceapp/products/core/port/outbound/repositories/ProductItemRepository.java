package com.ecommerceapp.products.core.port.outbound.repositories;

import java.util.Optional;

import org.bson.types.ObjectId;

import com.ecommerceapp.products.core.domain.entities.ProductItem;

public interface ProductItemRepository {
    ProductItem save(ProductItem productItem);

    Optional<ProductItem> findProductItemById(ObjectId id);

}
