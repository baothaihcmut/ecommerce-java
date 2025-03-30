package com.ecommerceapp.products.adapter.db.mongo.repositories;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.ecommerceapp.products.core.domain.entities.Product;
import com.ecommerceapp.products.core.port.outbound.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MongoProductRepository implements ProductRepository {
    private final MongoTemplate mongoTemplate;

    @Override
    public void save(Product product) {
        mongoTemplate.save(product);
    }

    @Override
    public Optional<Product> findProductById(ObjectId id) {
        Query q = new Query(
                Criteria.where("_id").is(id));
        return mongoTemplate.find(q, Product.class).stream().findFirst();
    }

}
