package com.ecommerceapp.products.adapter.db.mongo.repositories;

import java.util.List;
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

    @Override
    public FindProductByShopIdAndCountResult findProductsByShopIdAndCount(String shopId, Integer limit,
            Integer offset) {
        Query q = new Query(
                Criteria.where("shopId").is(shopId));
        q.skip(offset);
        q.limit(limit);
        List<Product> products = mongoTemplate.find(q, Product.class);
        Query qCount = new Query(
                Criteria.where("shopId").is(shopId));
        long count = mongoTemplate.count(qCount, Product.class);
        return new FindProductByShopIdAndCountResult(products, count);
    }

    @Override
    public void deleteProduct(Product product) {
        mongoTemplate.remove(product);
    }

}
