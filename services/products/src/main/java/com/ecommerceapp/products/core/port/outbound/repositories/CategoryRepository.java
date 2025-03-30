package com.ecommerceapp.products.core.port.outbound.repositories;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;

import com.ecommerceapp.products.core.domain.entities.Category;

public interface CategoryRepository {
    void save(Category category);

    Optional<Category> findCategoryById(ObjectId id);

    List<Category> findCategoryByIdList(List<ObjectId> ids);
}
