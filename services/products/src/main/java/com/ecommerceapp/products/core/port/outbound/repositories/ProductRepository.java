package com.ecommerceapp.products.core.port.outbound.repositories;

import java.util.Optional;

import org.bson.types.ObjectId;

import com.ecommerceapp.products.core.domain.entities.Product;

public interface ProductRepository {
    Product save(Product product);

    Optional<Product> findProductById(ObjectId id);
}
