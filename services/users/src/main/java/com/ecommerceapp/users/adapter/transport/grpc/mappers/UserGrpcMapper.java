package com.ecommerceapp.users.adapter.transport.grpc.mappers;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import com.ecommerceapp.generated.users.GetUserByIdListResponse;
import com.ecommerceapp.generated.users.GetUserByIdResponse;
import com.ecommerceapp.generated.users.UserResponse;
import com.ecommerceapp.users.core.port.inbound.results.GetUserByIdResult;
import com.ecommerceapp.users.core.port.inbound.results.GetUsersByIdListResult;
import com.ecommerceapp.users.core.port.inbound.results.UserResult;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface UserGrpcMapper {
    UserResponse map(UserResult result);

    GetUserByIdResponse toGetUserByIdResponse(GetUserByIdResult result);

    @Mappings(value = {
            @Mapping(source = "users", target = "usersList")
    })
    GetUserByIdListResponse toGetUserByIdListResponse(GetUsersByIdListResult result);
}
