package com.ecommerceapp.orders.adapter.transport.grpc.mappers;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.ecommerceapp.generated.products.ProductItemResponse;
import com.ecommerceapp.orders.core.domain.entities.ProductItem;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface ProductItemGrpcMapper {
    ProductItem toProductItem(ProductItemResponse response, String shopId);
}
