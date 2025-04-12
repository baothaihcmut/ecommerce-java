package com.ecommerceapp.users.core.usecase;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ecommerceapp.libs.exception.AppException;
import com.ecommerceapp.users.core.domain.entities.Address;
import com.ecommerceapp.users.core.exception.ErrorCode;
import com.ecommerceapp.users.core.port.inbound.handlers.UserAddressHandler;
import com.ecommerceapp.users.core.port.inbound.queries.GetAddressByIdQuery;
import com.ecommerceapp.users.core.port.inbound.results.AddressResult;
import com.ecommerceapp.users.core.port.inbound.results.GetAddressByIdResult;
import com.ecommerceapp.users.core.port.outbound.repositories.AddressRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAddressUseCase implements UserAddressHandler {
    private final AddressRepository addressRepository;

    @Override
    public GetAddressByIdResult getAddressById(GetAddressByIdQuery query) {
        Address address = addressRepository.findAddressById(UUID.fromString(query.id()))
                .orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOT_FOUND));
        return new GetAddressByIdResult(
                AddressResult.fromAddressDomain(address));
    }

}
