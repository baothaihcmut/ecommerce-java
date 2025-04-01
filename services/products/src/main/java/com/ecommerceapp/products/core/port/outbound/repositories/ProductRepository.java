package com.ecommerceapp.products.core.port.outbound.repositories;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;

import com.ecommerceapp.products.core.domain.entities.Product;

public interface ProductRepository {
    void save(Product product);

    Optional<Product> findProductById(ObjectId id);

    List<Product> findProductsByShopId(String shopId);
}
