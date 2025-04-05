package com.ecommerceapp.shops.adapter.db.repositories;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.ecommerceapp.shops.core.domain.entities.ShopFollower;
import com.ecommerceapp.shops.core.port.outbound.repositories.ShopFollowerRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MongoShopFollderRepository implements ShopFollowerRepository {
    private final MongoTemplate mongoTemplate;

    @Override
    public void save(ShopFollower shopFollower) {
        mongoTemplate.save(shopFollower);
    }

    @Override
    public void delete(ShopFollower shopFollower) {
        mongoTemplate.remove(shopFollower);
    }

    @Override
    public Optional<ShopFollower> findByShopIdAndUserId(ObjectId shopId, String userId) {
        Query q = new Query(
                Criteria.where("userId").is(userId)
                        .and("shopId").is(shopId));
        return mongoTemplate.find(q, ShopFollower.class).stream().findFirst();
    }

    @Override
    public FindShopFollowersByShopIdAndCountResult findShopFollowersByShopIdAndCount(ObjectId shopId, Integer limit,
            Integer offset) {
        Query q = new Query(
                Criteria.where("shopId").is(shopId));
        q.skip(offset);
        q.limit(limit);
        List<ShopFollower> followers = mongoTemplate.find(q, ShopFollower.class);
        Query qCount = new Query(Criteria.where("shopId").is(shopId));
        long count = mongoTemplate.count(qCount, ShopFollower.class);
        return new FindShopFollowersByShopIdAndCountResult(followers, count);
    }

}
