package com.ecommerceapp.users.adapter.transport.grpc.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.ecommerceapp.generated.users.GetUserByIdListResponse;
import com.ecommerceapp.generated.users.GetUserByIdResponse;
import com.ecommerceapp.generated.users.UserResponse;
import com.ecommerceapp.users.core.port.inbound.results.GetUserByIdResult;
import com.ecommerceapp.users.core.port.inbound.results.GetUsersByIdListResult;
import com.ecommerceapp.users.core.port.inbound.results.UserResult;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserGrpcMapper {
    UserResponse map(UserResult result);

    GetUserByIdResponse toGetUserByIdResponse(GetUserByIdResult result);

    GetUserByIdListResponse toGetUserByIdListResponse(GetUsersByIdListResult result);
}
