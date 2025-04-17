package com.ecommerceapp.orders.adapter.transport.grpc.clients;

import org.springframework.stereotype.Service;

import com.ecommerceapp.generated.shops.GetShopByIdRequest;
import com.ecommerceapp.generated.shops.GetShopByIdResponse;
import com.ecommerceapp.generated.shops.ShopsServiceGrpc.ShopsServiceBlockingStub;
import com.ecommerceapp.orders.adapter.transport.grpc.mappers.ShopGrpcMapper;
import com.ecommerceapp.orders.core.domain.entities.Shop;
import com.ecommerceapp.orders.core.port.outbound.clients.ShopClient;

import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;

@Service
@RequiredArgsConstructor
public class ShopGrpcClient implements ShopClient {

    @GrpcClient("shops-service")
    private ShopsServiceBlockingStub serviceBlockingStub;
    private final ShopGrpcMapper shopGrpcMapper;

    @Override
    public Shop findShopById(String id) {
        GetShopByIdResponse response = serviceBlockingStub
                .getShopById(GetShopByIdRequest.newBuilder().setId(id).build());
        return shopGrpcMapper.toShop(response.getShop());
    }

}
