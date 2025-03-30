package com.ecommerceapp.products.adapter.db.mongo.repositories;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.ecommerceapp.products.core.domain.entities.ProductItem;
import com.ecommerceapp.products.core.port.outbound.repositories.ProductItemRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MongoProductItemRepository implements ProductItemRepository {
    private final MongoTemplate mongoTemplate;

    @Override
    public void save(ProductItem productItem) {
        this.mongoTemplate.save(productItem);
    }

    @Override
    public Optional<ProductItem> findProductItemById(ObjectId id) {
        Query q = new Query(
                Criteria.where("_id").is(id));
        return mongoTemplate.find(q, ProductItem.class).stream().findFirst();
    }

}
