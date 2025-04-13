package com.ecommerceapp.users.core.usecase;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerceapp.libs.exception.AppException;
import com.ecommerceapp.libs.security.SecurityUtil;
import com.ecommerceapp.libs.security.SecurityUtil.UserContext;
import com.ecommerceapp.users.core.domain.entities.Address;
import com.ecommerceapp.users.core.domain.entities.User;
import com.ecommerceapp.users.core.exception.ErrorCode;
import com.ecommerceapp.users.core.port.inbound.commands.AddUserAddressCommand;
import com.ecommerceapp.users.core.port.inbound.handlers.UserAddressHandler;
import com.ecommerceapp.users.core.port.inbound.queries.GetAddressByIdQuery;
import com.ecommerceapp.users.core.port.inbound.results.AddUserAddressResult;
import com.ecommerceapp.users.core.port.inbound.results.AddressResult;
import com.ecommerceapp.users.core.port.inbound.results.GetAddressByIdResult;
import com.ecommerceapp.users.core.port.outbound.repositories.AddressRepository;
import com.ecommerceapp.users.core.port.outbound.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAddressUseCase implements UserAddressHandler {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Override
    public GetAddressByIdResult getAddressById(GetAddressByIdQuery query) {
        Address address = addressRepository.findAddressById(UUID.fromString(query.id()))
                .orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOT_FOUND));
        return new GetAddressByIdResult(
                AddressResult.fromAddressDomain(address));
    }

    @Override
    @Transactional
    public AddUserAddressResult addUserAddress(AddUserAddressCommand command) {
        UserContext userContext = SecurityUtil.getUserContext();
        User user = userRepository.findUserById(UUID.fromString(userContext.userId()))
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        Address address = new Address(
                command.address(),
                command.street(),
                command.ward(),
                command.district(),
                command.province(),
                user);
        addressRepository.save(address);
        return new AddUserAddressResult(AddressResult.fromAddressDomain(address));

    }

}
