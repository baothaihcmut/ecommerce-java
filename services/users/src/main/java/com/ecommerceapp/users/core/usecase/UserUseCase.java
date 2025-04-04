package com.ecommerceapp.users.core.usecase;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ecommerceapp.libs.exception.AppException;
import com.ecommerceapp.users.core.domain.entities.User;
import com.ecommerceapp.users.core.exception.ErrorCode;
import com.ecommerceapp.users.core.port.inbound.handlers.UserHandler;
import com.ecommerceapp.users.core.port.inbound.queries.GetUserByIdQuery;
import com.ecommerceapp.users.core.port.inbound.queries.GetUsersByIdListQuery;
import com.ecommerceapp.users.core.port.inbound.results.GetUserByIdResult;
import com.ecommerceapp.users.core.port.inbound.results.GetUsersByIdListResult;
import com.ecommerceapp.users.core.port.inbound.results.UserResult;
import com.ecommerceapp.users.core.port.outbound.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserUseCase implements UserHandler {
    private final UserRepository userRepository;

    @Override
    public GetUserByIdResult getUserById(GetUserByIdQuery query) {
        User user = userRepository.findUserById(UUID.fromString(query.getId()))
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return GetUserByIdResult.builder()
                .user(UserResult.toUserResult(user))
                .build();
    }

    @Override
    public GetUsersByIdListResult getUserByIdList(GetUsersByIdListQuery query) {
        List<User> users = userRepository.findUserByIdList(query.getIds().stream()
                .map(id -> UUID.fromString(id)).toList());
        return GetUsersByIdListResult.builder()
                .users(users.stream()
                        .map(user -> UserResult.toUserResult(user)).toList())
                .build();
    }

}
