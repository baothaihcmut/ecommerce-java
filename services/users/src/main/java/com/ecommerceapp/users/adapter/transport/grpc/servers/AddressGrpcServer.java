package com.ecommerceapp.users.adapter.transport.grpc.servers;

import com.ecommerceapp.generated.users.GetAddressByIdRequest;
import com.ecommerceapp.generated.users.GetAddressByIdResponse;
import com.ecommerceapp.generated.users.AddressServiceGrpc.AddressServiceImplBase;
import com.ecommerceapp.users.adapter.transport.grpc.mappers.UserAddressGrpcMapper;
import com.ecommerceapp.users.core.port.inbound.handlers.UserAddressHandler;
import com.ecommerceapp.users.core.port.inbound.queries.GetAddressByIdQuery;
import com.ecommerceapp.users.core.port.inbound.results.GetAddressByIdResult;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class AddressGrpcServer extends AddressServiceImplBase {
    private final UserAddressHandler userAddressHandler;
    private final UserAddressGrpcMapper userAddressGrpcMapper;

    @Override
    public void getAddressById(GetAddressByIdRequest request, StreamObserver<GetAddressByIdResponse> rObserver) {
        GetAddressByIdResult result = userAddressHandler.getAddressById(
                new GetAddressByIdQuery(request.getId()));
        rObserver.onNext(
                userAddressGrpcMapper.toGetAddresByIdResponse(result));
        rObserver.onCompleted();
    }
}
