package com.ecommerceapp.orders.adapter.transport.grpc.mappers;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;

import com.ecommerceapp.generated.shops.ShopResponse;
import com.ecommerceapp.orders.core.domain.entities.Shop;

@Mapper(componentModel = "spring", collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface ShopGrpcMapper {
    Shop toShop(ShopResponse shopResponse);
}
