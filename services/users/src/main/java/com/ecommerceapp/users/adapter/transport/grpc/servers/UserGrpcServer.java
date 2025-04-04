package com.ecommerceapp.users.adapter.transport.grpc.servers;

import com.ecommerceapp.generated.users.GetUserByIdListRequest;
import com.ecommerceapp.generated.users.GetUserByIdListResponse;
import com.ecommerceapp.generated.users.GetUserByIdRequest;
import com.ecommerceapp.generated.users.GetUserByIdResponse;
import com.ecommerceapp.generated.users.UsersServiceGrpc.UsersServiceImplBase;
import com.ecommerceapp.users.adapter.transport.grpc.mappers.UserGrpcMapper;
import com.ecommerceapp.users.core.port.inbound.handlers.UserHandler;
import com.ecommerceapp.users.core.port.inbound.queries.GetUserByIdQuery;
import com.ecommerceapp.users.core.port.inbound.queries.GetUsersByIdListQuery;
import com.ecommerceapp.users.core.port.inbound.results.GetUserByIdResult;
import com.ecommerceapp.users.core.port.inbound.results.GetUsersByIdListResult;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class UserGrpcServer extends UsersServiceImplBase {
    private final UserHandler userHandler;
    private final UserGrpcMapper userGrpcMapper;

    @Override
    public void getUserById(GetUserByIdRequest request, StreamObserver<GetUserByIdResponse> response) {
        GetUserByIdResult res = userHandler.getUserById(GetUserByIdQuery.builder()
                .id(request.getId())
                .build());
        response.onNext(userGrpcMapper.toGetUserByIdResponse(res));
        response.onCompleted();
    }

    @Override
    public void getUserByIdList(GetUserByIdListRequest request, StreamObserver<GetUserByIdListResponse> rObserver) {
        GetUsersByIdListResult result = userHandler.getUserByIdList(GetUsersByIdListQuery.builder()
                .ids(request.getIdList())
                .build());
        rObserver.onNext(userGrpcMapper.toGetUserByIdListResponse(result));
        rObserver.onCompleted();
    }
}
