package com.ecommerceapp.products.adapter.transport.grpc.servers;

import com.ecommerceapp.generated.products.GetProductItemWithProductByIdListRequest;
import com.ecommerceapp.generated.products.GetProductItemWithProductByIdListResponse;
import com.ecommerceapp.generated.products.ProductItemServiceGrpc.ProductItemServiceImplBase;
import com.ecommerceapp.products.adapter.transport.grpc.mappers.ProductItemGrpcMapper;
import com.ecommerceapp.products.core.port.inbound.handlers.ProductItemHandler;
import com.ecommerceapp.products.core.port.inbound.queries.GetProductItemWithProductQuery;
import com.ecommerceapp.products.core.port.inbound.results.GetProductItemWithProductByIdListResult;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class ProductItemGrpcServer extends ProductItemServiceImplBase {
    private final ProductItemHandler productItemHandler;
    private final ProductItemGrpcMapper mapper;

    @Override
    public void getProductItemWithProductByIdList(
            GetProductItemWithProductByIdListRequest request,
            StreamObserver<GetProductItemWithProductByIdListResponse> rObserver) {
        GetProductItemWithProductByIdListResult result = productItemHandler.getProductItemWithProductByIdList(
                GetProductItemWithProductQuery.builder()
                        .ids(request.getIdsList())
                        .build());
        rObserver.onNext(mapper.toGetProductItemWithProductByIdListResponse(result));
        rObserver.onCompleted();

    }
}
