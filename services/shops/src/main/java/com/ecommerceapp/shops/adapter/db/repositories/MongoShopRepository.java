package com.ecommerceapp.shops.adapter.db.repositories;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.ecommerceapp.shops.core.domain.entities.Shop;
import com.ecommerceapp.shops.core.port.outbound.repositories.ShopRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MongoShopRepository implements ShopRepository {
    private final MongoTemplate mongoTemplate;

    @Override
    public void save(Shop shop) {
        this.mongoTemplate.save(shop);
    }

    @Override
    public Optional<Shop> findShopById(ObjectId objectId) {
        Query q = new Query(
                Criteria.where("_id").is(objectId));
        return mongoTemplate.find(q, Shop.class).stream().findFirst();
    }

}
