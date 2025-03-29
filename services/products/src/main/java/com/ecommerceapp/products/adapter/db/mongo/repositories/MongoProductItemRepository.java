package com.ecommerceapp.products.adapter.db.mongo.repositories;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ecommerceapp.products.core.domain.entities.ProductItem;
import com.ecommerceapp.products.core.port.outbound.repositories.ProductItemRepository;

public interface MongoProductItemRepository extends MongoRepository<ProductItem, ObjectId>, ProductItemRepository {

    @Query("{'_id': ?0}")
    Optional<ProductItem> findProductItemById(ObjectId id);
}
