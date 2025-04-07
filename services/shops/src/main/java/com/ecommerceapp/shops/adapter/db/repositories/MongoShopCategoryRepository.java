package com.ecommerceapp.shops.adapter.db.repositories;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.ecommerceapp.shops.core.domain.entities.ShopCategory;
import com.ecommerceapp.shops.core.port.outbound.repositories.ShopCategoryRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MongoShopCategoryRepository implements ShopCategoryRepository {
    private final MongoTemplate mongoTemplate;

    @Override
    public void save(ShopCategory shopCategory) {
        mongoTemplate.save(shopCategory);
    }

    @Override
    public Optional<ShopCategory> findShopCategoryById(ObjectId id) {
        Query q = new Query(
                Criteria.where("_id").is(id));
        return mongoTemplate.find(q, ShopCategory.class).stream().findFirst();
    }

    @Override
    public List<ShopCategory> findShopCategoryByShopId(ObjectId shopId) {
        Query query = new Query(
                Criteria.where("shopId").is(shopId));
        return mongoTemplate.find(query, ShopCategory.class);
    }

}
