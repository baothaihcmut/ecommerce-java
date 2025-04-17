package com.ecommerceapp.shops.adapter.transport.grpc.mappers;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.ecommerceapp.generated.shops.GetShopByIdResponse;
import com.ecommerceapp.generated.shops.ShopResponse;
import com.ecommerceapp.libs.grpc.mappers.ProtoMapper;
import com.ecommerceapp.shops.core.port.inbound.results.GetShopByIdResult;
import com.ecommerceapp.shops.core.port.inbound.results.ShopResult;

@Mapper(componentModel = "spring", uses = {
        ProtoMapper.class }, collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface ShopGrpcMapper {
    @Mappings(value = {
            @Mapping(source = "createdAt", target = "createdAt", qualifiedByName = "mapInstantToTimestamp"),
            @Mapping(source = "updatedAt", target = "updatedAt", qualifiedByName = "mapInstantToTimestamp"),
    })
    ShopResponse map(ShopResult result);

    GetShopByIdResponse toGetShopByIdResponse(GetShopByIdResult result);
}
