package com.ecommerceapp.orders.adapter.transport.grpc.clients;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecommerceapp.generated.users.GetAddressByIdRequest;
import com.ecommerceapp.generated.users.GetAddressByIdResponse;
import com.ecommerceapp.libs.grpc.interceptors.InjectUserAuthInterceptor;
import com.ecommerceapp.generated.users.AddressServiceGrpc.AddressServiceBlockingStub;
import com.ecommerceapp.orders.adapter.transport.grpc.mappers.UserAddressGrpcMapper;
import com.ecommerceapp.orders.core.domain.entities.Address;
import com.ecommerceapp.orders.core.port.outbound.clients.UserAddressClient;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;

@Service
@RequiredArgsConstructor
public class UserAddressGrpcClient implements UserAddressClient {

    @GrpcClient("users-service")
    private AddressServiceBlockingStub addressServiceBlockingStub;
    private final UserAddressGrpcMapper userAddressGrpcMapper;

    @PostConstruct
    public void applyInterceptor() {
        addressServiceBlockingStub = addressServiceBlockingStub
                .withInterceptors(new InjectUserAuthInterceptor());
    }

    @Override
    public Optional<Address> findAddressById(String id) {
        try {
            GetAddressByIdResponse addressResponse = addressServiceBlockingStub.getAddressById(
                    GetAddressByIdRequest.newBuilder().setId(id).build());
            return Optional.of(
                    userAddressGrpcMapper.toAddressDomain(addressResponse.getAddress()));
        } catch (StatusRuntimeException e) {
            if (e.getStatus().equals(Status.NOT_FOUND)) {
                return Optional.empty();
            } else {
                throw e;
            }
        }
    }

}
