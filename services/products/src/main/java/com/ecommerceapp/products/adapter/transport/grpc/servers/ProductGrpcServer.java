package com.ecommerceapp.products.adapter.transport.grpc.servers;

import com.ecommerceapp.generated.products.CreateProductRequest;
import com.ecommerceapp.generated.products.CreateProductResponse;
import com.ecommerceapp.generated.products.GetProductsOfShopRequest;
import com.ecommerceapp.generated.products.GetProductsOfShopResponse;
import com.ecommerceapp.generated.products.ProductServiceGrpc.ProductServiceImplBase;
import com.ecommerceapp.products.adapter.transport.grpc.mappers.ProductGrpcMapper;
import com.ecommerceapp.products.core.port.inbound.handlers.ProductHandler;
import com.ecommerceapp.products.core.port.inbound.results.CreateProductResult;
import com.ecommerceapp.products.core.port.inbound.results.GetProductsOfShopResult;

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

    @Override
    public void getProductsOfShop(GetProductsOfShopRequest request,
            StreamObserver<GetProductsOfShopResponse> response) {
        GetProductsOfShopResult result = productHandler
                .getProductsOfShop(productGrpcMapper.toGetProductsOfShopQuery(request));
        response.onNext(productGrpcMapper.toGetProductsOfShopResponse(result));
        response.onCompleted();
    }
}
