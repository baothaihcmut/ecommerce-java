package com.ecommerceapp.products.adapter.db.mongo.repositories;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.ecommerceapp.products.core.domain.entities.ProductItem;
import com.ecommerceapp.products.core.domain.projection.ProductItemWithProductProjection;
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

    @Override
    public List<ProductItemWithProductProjection> findProductItemWithProductByIdList(List<ObjectId> ids) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("_id").in(ids)),
                Aggregation.lookup("products", "productId", "_id", "product"),
                Aggregation.unwind("product", true));
        AggregationResults<ProductItemWithProductProjection> res = mongoTemplate.aggregate(
                aggregation,
                "product_items",
                ProductItemWithProductProjection.class);
        return res.getMappedResults();
    }

    @Override
    public void bulkSave(List<ProductItem> productItems) {
        BulkOperations bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, ProductItem.class);
        for (ProductItem item : productItems) {
            Query q = new Query(
                    Criteria.where("_id").is(item.getId()));
            Update update = new Update()
                    .set("productId", item.getProductId())
                    .set("quantity", item.getQuantity())
                    .set("price", item.getPrice())
                    .set("images", item.getImages())
                    .set("variationValues", item.getVariationValues())
                    .set("createdAt", item.getCreatedAt())
                    .set("updatedAt", item.getUpdatedAt());
            bulkOperations.upsert(q, update);
        }
        bulkOperations.execute();
    }

    @Override
    public List<ProductItem> findProductItemByIdList(List<ObjectId> id) {
        Query q = new Query(
                Criteria.where("_id").in(id));
        return mongoTemplate.find(q, ProductItem.class);
    }

}
