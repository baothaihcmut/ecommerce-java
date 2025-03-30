package com.ecommerceapp.shops.adapter.transport.grpc.mappers;

import java.time.Instant;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import com.ecommerceapp.generated.shops.GetShopByIdRequest;
import com.ecommerceapp.generated.shops.GetShopByIdResponse;
import com.ecommerceapp.generated.shops.ShopResponse;
import com.ecommerceapp.shops.core.port.inbound.queries.GetShopByIdQuery;
import com.ecommerceapp.shops.core.port.inbound.results.GetShopByIdResult;
import com.ecommerceapp.shops.core.port.inbound.results.ShopResult;
import com.google.protobuf.Timestamp;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShopGrpcMapper {
    GetShopByIdQuery toGetShopByIdQuery(GetShopByIdRequest request);

    @Mapping(source = "createdAt", target = "createdAt", qualifiedByName = "mapInstantToTimestamp")
    @Mapping(source = "updatedAt", target = "updatedAt", qualifiedByName = "mapInstantToTimestamp")
    ShopResponse toShopResponse(ShopResult result);

    GetShopByIdResponse toGetShopByIdResponse(GetShopByIdResult result);

    @Named("mapInstantToTimestamp")
    default Timestamp mapInstantToTimestamp(Instant instant) {
        if (instant == null) {
            return null;
        }
        return Timestamp.newBuilder()
                .setSeconds(instant.getEpochSecond())
                .setNanos(instant.getNano())
                .build();
    }
}
