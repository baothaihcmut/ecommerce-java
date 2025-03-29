package com.ecommerceapp.products.adapter.db.mongo.repositories;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ecommerceapp.products.core.domain.entities.Category;
import com.ecommerceapp.products.core.port.outbound.repositories.CategoryRepository;

public interface MongoCategoryRepository extends MongoRepository<Category, ObjectId>, CategoryRepository {

    @Query("{'_id': ?0}")
    Optional<Category> findCategoryById(ObjectId id);

    @Query("{'id': { $in: ?0}}")
    List<Category> findCategoryByIdList(List<ObjectId> ids);
}
