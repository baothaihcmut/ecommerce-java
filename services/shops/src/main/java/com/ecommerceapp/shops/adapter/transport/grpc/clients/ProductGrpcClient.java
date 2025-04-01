package com.ecommerceapp.shops.adapter.transport.grpc.clients;

import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ecommerceapp.generated.products.CreateProductRequest;
import com.ecommerceapp.generated.products.CreateProductResponse;
import com.ecommerceapp.generated.products.GetProductsOfShopRequest;
import com.ecommerceapp.generated.products.GetProductsOfShopResponse;
import com.ecommerceapp.generated.products.ProductResponse;
import com.ecommerceapp.generated.products.ProductServiceGrpc.ProductServiceBlockingStub;
import com.ecommerceapp.libs.exception.AppException;
import com.ecommerceapp.shops.adapter.transport.grpc.mappers.ProductGrpcMapper;
import com.ecommerceapp.shops.core.domain.entities.Product;
import com.ecommerceapp.shops.core.port.inbound.commands.CreateProductCommand;
import com.ecommerceapp.shops.core.port.outbound.clients.ProductClient;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;

@Service
@RequiredArgsConstructor
public class ProductGrpcClient implements ProductClient {
    @GrpcClient("products-service")
    private ProductServiceBlockingStub productServiceBlockingStub;

    private final ProductGrpcMapper productMapper;

    public Product createProduct(CreateProductCommand createProductCommand) {
        CreateProductRequest request = productMapper.toCreateProductRequest(createProductCommand).build();
        try {
            CreateProductResponse createProductResponse = productServiceBlockingStub.createProduct(request);
            ProductResponse productResponse = createProductResponse.getProduct();
            return productMapper.toProduct(productResponse);

        } catch (StatusRuntimeException exception) {
            if (exception.getStatus().equals(Status.NOT_FOUND)) {
                throw new AppException(exception.getMessage(), HttpStatus.NOT_FOUND, new HashMap<>());
            }
            throw exception;
        }
    }

    @Override
    public List<Product> getProductsOfShop(String shopId) {
        GetProductsOfShopRequest request = GetProductsOfShopRequest.newBuilder().setShopId(shopId).build();
        GetProductsOfShopResponse response = productServiceBlockingStub.getProductsOfShop(request);
        return response.getProductsList().stream().map(prod -> productMapper.toProduct(prod)).toList();
    }

}
