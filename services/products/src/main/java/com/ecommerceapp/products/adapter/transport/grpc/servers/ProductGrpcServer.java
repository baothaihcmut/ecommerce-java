package com.ecommerceapp.products.adapter.transport.grpc.servers;

import com.ecommerceapp.generated.products.CreateProductRequest;
import com.ecommerceapp.generated.products.CreateProductResponse;
import com.ecommerceapp.generated.products.ProductServiceGrpc.ProductServiceImplBase;
import com.ecommerceapp.products.adapter.transport.grpc.mappers.ProductGrpcMapper;
import com.ecommerceapp.products.core.port.inbound.handlers.ProductHandler;
import com.ecommerceapp.products.core.port.inbound.results.CreateProductResult;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class ProductGrpcServer extends ProductServiceImplBase {
    private final ProductGrpcMapper productGrpcMapper;
    private final ProductHandler productHandler;

    @Override
    public void createProduct(CreateProductRequest request, StreamObserver<CreateProductResponse> res) {

        CreateProductResult result = productHandler.createProduct(productGrpcMapper.toCreateProductCommand(request));
        res.onNext(productGrpcMapper.toCreateProductResponse(result));
        res.onCompleted();
    }
}
