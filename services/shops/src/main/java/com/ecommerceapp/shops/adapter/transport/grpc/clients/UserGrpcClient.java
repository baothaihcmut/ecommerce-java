package com.ecommerceapp.shops.adapter.transport.grpc.clients;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerceapp.generated.users.GetUserByIdListRequest;
import com.ecommerceapp.generated.users.GetUserByIdListResponse;
import com.ecommerceapp.generated.users.UsersServiceGrpc.UsersServiceBlockingStub;
import com.ecommerceapp.shops.adapter.transport.grpc.mappers.UserGrpcMapper;
import com.ecommerceapp.shops.core.domain.entities.User;
import com.ecommerceapp.shops.core.port.outbound.clients.UserClient;

import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;

@Service
@RequiredArgsConstructor
public class UserGrpcClient implements UserClient {
    @GrpcClient("users-service")
    private UsersServiceBlockingStub usersServiceBlockingStub;
    private final UserGrpcMapper userGrpcMapper;

    @Override
    public List<User> getUserByIdList(List<String> ids) {
        GetUserByIdListResponse res = usersServiceBlockingStub.getUserByIdList(
                GetUserByIdListRequest.newBuilder()
                        .addAllId(ids)
                        .build());
        return res.getUsersList().stream().map(user -> userGrpcMapper.toUser(user)).toList();
    }

}
