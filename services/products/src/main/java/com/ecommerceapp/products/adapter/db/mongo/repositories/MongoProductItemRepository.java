package com.ecommerceapp.products.adapter.db.mongo.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.ecommerceapp.products.core.domain.entities.ProductItem;

public interface MongoProductItemRepository extends MongoRepository<ProductItem, ObjectId> {

}
