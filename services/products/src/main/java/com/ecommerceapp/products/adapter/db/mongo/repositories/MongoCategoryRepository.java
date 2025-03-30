package com.ecommerceapp.products.adapter.db.mongo.repositories;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.ecommerceapp.products.core.domain.entities.Category;
import com.ecommerceapp.products.core.port.outbound.repositories.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MongoCategoryRepository implements CategoryRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public void save(Category category) {
        this.mongoTemplate.save(category);
    }

    @Override
    public Optional<Category> findCategoryById(ObjectId id) {
        Query q = new Query(Criteria.where("_id").is(id));
        return mongoTemplate.find(q, Category.class).stream().findFirst();
    }

    @Override
    public List<Category> findCategoryByIdList(List<ObjectId> ids) {
        Query q = new Query(
                Criteria.where("_id").in(ids));
        return mongoTemplate.find(q, Category.class);
    }
}