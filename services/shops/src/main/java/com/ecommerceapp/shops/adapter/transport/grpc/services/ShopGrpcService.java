package com.ecommerceapp.shops.adapter.transport.grpc.services;

import org.lognet.springboot.grpc.GRpcService;

import com.ecommerceapp.generated.shops.GetShopByIdRequest;
import com.ecommerceapp.generated.shops.GetShopByIdResponse;
import com.ecommerceapp.generated.shops.ShopServiceGrpc.ShopServiceImplBase;
import com.ecommerceapp.shops.adapter.transport.grpc.mappers.ShopGrpcMapper;
import com.ecommerceapp.shops.core.port.inbound.handlers.ShopHandler;
import com.ecommerceapp.shops.core.port.inbound.results.GetShopByIdResult;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;

@GRpcService
@RequiredArgsConstructor
public class ShopGrpcService extends ShopServiceImplBase {

    private final ShopHandler shopHandler;
    private final ShopGrpcMapper shopGrpcMapper;

    @Override
    public void getShopById(GetShopByIdRequest request, StreamObserver<GetShopByIdResponse> response) {
        GetShopByIdResult res = shopHandler.getShopById(shopGrpcMapper.toGetShopByIdQuery(request));
        response.onNext(this.shopGrpcMapper.toGetShopByIdResponse(res));
        response.onCompleted();
    }
}
