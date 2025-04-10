package com.ecommerceapp.products.core.port.outbound.repositories;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;

import com.ecommerceapp.products.core.domain.entities.ProductItem;
import com.ecommerceapp.products.core.domain.projection.ProductItemWithProductProjection;

public interface ProductItemRepository {
    void save(ProductItem productItem);

    void bulkSave(List<ProductItem> productItems);

    Optional<ProductItem> findProductItemById(ObjectId id);

    List<ProductItem> findProductItemByIdList(List<ObjectId> id);

    List<ProductItemWithProductProjection> findProductItemWithProductByIdList(List<ObjectId> ids);

}
