package com.ecommerceapp.products.adapter.transport.grpc.mappers;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

import com.ecommerceapp.generated.products.GetProductItemWithProductByIdListResponse;
import com.ecommerceapp.generated.products.ProductItemResponse;
import com.ecommerceapp.generated.products.ProductItemWithProductResponse;
import com.ecommerceapp.libs.grpc.mappers.ProtoMapper;
import com.ecommerceapp.products.core.port.inbound.results.GetProductItemWithProductByIdListResult;
import com.ecommerceapp.products.core.port.inbound.results.ProductItemResult;
import com.ecommerceapp.products.core.port.inbound.results.ProductItemWithProductResult;

@Mapper(componentModel = "spring", uses = {
                ProtoMapper.class,
                ProductGrpcMapper.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE, collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ProductItemGrpcMapper {
        @Mappings(value = {
                        @Mapping(source = "createdAt", target = "createdAt", qualifiedByName = "mapInstantToTimestamp"),
                        @Mapping(source = "updatedAt", target = "updatedAt", qualifiedByName = "mapInstantToTimestamp"),
        })
        ProductItemResponse map(ProductItemResult result);

        ProductItemWithProductResponse map(ProductItemWithProductResult result);

        @Mappings(value = {
                        @Mapping(source = "productItemWithProducts", target = "productItemWithProductsList")
        })
        GetProductItemWithProductByIdListResponse toGetProductItemWithProductByIdListResponse(
                        GetProductItemWithProductByIdListResult res);

}
