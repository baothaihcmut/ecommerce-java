package com.ecommerceapp.products.adapter.db.mongo.repositories;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ecommerceapp.products.core.domain.entities.Product;
import com.ecommerceapp.products.core.port.outbound.repositories.ProductRepository;

public interface MongoProductRepository extends MongoRepository<Product, ObjectId>, ProductRepository {
    @Query("{'_id': ?0}")
    Optional<Product> findProductById(ObjectId id);

}
