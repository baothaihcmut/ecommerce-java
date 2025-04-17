package com.ecommerceapp.shops.adapter.transport.grpc.servers;

import com.ecommerceapp.generated.shops.GetShopByIdRequest;
import com.ecommerceapp.generated.shops.GetShopByIdResponse;
import com.ecommerceapp.generated.shops.ShopsServiceGrpc.ShopsServiceImplBase;
import com.ecommerceapp.shops.adapter.transport.grpc.mappers.ShopGrpcMapper;
import com.ecommerceapp.shops.core.port.inbound.handlers.ShopHandler;
import com.ecommerceapp.shops.core.port.inbound.queries.GetShopByIdQuery;
import com.ecommerceapp.shops.core.port.inbound.results.GetShopByIdResult;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class ShopsServer extends ShopsServiceImplBase {
    private final ShopHandler shopHandler;
    private final ShopGrpcMapper shopGrpcMapper;

    @Override
    public void getShopById(GetShopByIdRequest request, StreamObserver<GetShopByIdResponse> rObserver) {
        GetShopByIdResult result = shopHandler.getShopById(new GetShopByIdQuery(
                request.getId()));
        rObserver.onNext(
                shopGrpcMapper.toGetShopByIdResponse(result));
        rObserver.onCompleted();
    }
}
