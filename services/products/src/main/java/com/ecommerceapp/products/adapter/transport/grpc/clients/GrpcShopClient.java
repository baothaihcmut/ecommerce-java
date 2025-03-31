package com.ecommerceapp.products.adapter.transport.grpc.clients;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecommerceapp.generated.shops.GetShopByIdRequest;
import com.ecommerceapp.generated.shops.GetShopByIdResponse;
import com.ecommerceapp.generated.shops.ShopServiceGrpc.ShopServiceBlockingStub;
import com.ecommerceapp.products.core.domain.entities.Shop;
import com.ecommerceapp.products.core.port.outbound.clients.ShopClient;

import io.grpc.StatusRuntimeException;
import io.grpc.Status.Code;
import net.devh.boot.grpc.client.inject.GrpcClient;

@Service
public class GrpcShopClient implements ShopClient {
    @GrpcClient("shops-service")
    private ShopServiceBlockingStub shopStub;

    @Override
    public Optional<Shop> findShopById(String shopId) {
        GetShopByIdRequest request = GetShopByIdRequest.newBuilder()
                .setShopId(shopId)
                .build();
        try {
            GetShopByIdResponse response = shopStub.getShopById(request);
            return Optional.of(new Shop(response.getShop().getId(), response.getShop().getOwnerId()));
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode().equals(Code.NOT_FOUND)) {
                return Optional.empty();
            }
            throw e;
        }

    }
}
