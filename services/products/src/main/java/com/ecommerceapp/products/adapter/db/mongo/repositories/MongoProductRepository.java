package com.ecommerceapp.products.adapter.db.mongo.repositories;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<List<Product>> finThread = executorService.submit(() -> {
                Query q = new Query(
                        Criteria.where("shopId").is(shopId));
                q.limit(limit);
                q.skip(offset);
                return mongoTemplate.find(q, Product.class);
            });
            Future<Long> countThread = executorService.submit(() -> {
                Query q = new Query(
                        Criteria.where("shopId").is(shopId));
                return mongoTemplate.count(q, "products");
            });
            return new FindProductByShopIdAndCountResult(finThread.get(), countThread.get());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted", e);
        } catch (ExecutionException e) {
            throw new RuntimeException("Error during async execution", e.getCause());
        }
    }

    @Override
    public void deleteProduct(Product product) {
        mongoTemplate.remove(product);
    }

}
