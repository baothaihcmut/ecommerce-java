package com.ecommerceapp.orders.adapter.transport.grpc.clients;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerceapp.generated.products.GetProductItemWithProductByIdListRequest;
import com.ecommerceapp.generated.products.GetProductItemWithProductByIdListResponse;
import com.ecommerceapp.generated.products.ProductItemServiceGrpc.ProductItemServiceBlockingStub;
import com.ecommerceapp.libs.grpc.interceptors.InjectUserAuthInterceptor;
import com.ecommerceapp.orders.adapter.transport.grpc.mappers.ProductItemGrpcMapper;
import com.ecommerceapp.orders.core.domain.entities.ProductItem;
import com.ecommerceapp.orders.core.port.outbound.clients.ProductItemClient;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;

@Service
@RequiredArgsConstructor
public class ProductItemGrpcClient implements ProductItemClient {
    @GrpcClient("products-service")
    private ProductItemServiceBlockingStub productItemServiceBlockingStub;

    @PostConstruct
    public void applyInterceptor() {
        productItemServiceBlockingStub = productItemServiceBlockingStub
                .withInterceptors(new InjectUserAuthInterceptor());
    }

    private final ProductItemGrpcMapper productItemGrpcMapper;

    @Override
    public List<ProductItem> findProductItemByIdList(List<String> ids) {
        GetProductItemWithProductByIdListResponse res = productItemServiceBlockingStub
                .getProductItemWithProductByIdList(
                        GetProductItemWithProductByIdListRequest.newBuilder().addAllIds(ids).build());
        return res
                .getProductItemWithProductsList()
                .stream()
                .map(item -> productItemGrpcMapper.toProductItem(item.getProductItem(), item.getProduct().getShopId()))
                .toList();
    }

}
