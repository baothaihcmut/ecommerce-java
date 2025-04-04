package com.ecommerceapp.shops.adapter.transport.grpc.mappers;

import org.mapstruct.Mapper;

import com.ecommerceapp.generated.users.UserResponse;
import com.ecommerceapp.shops.core.domain.entities.User;

@Mapper(componentModel = "spring")
public interface UserGrpcMapper {

    User toUser(UserResponse response);

}
