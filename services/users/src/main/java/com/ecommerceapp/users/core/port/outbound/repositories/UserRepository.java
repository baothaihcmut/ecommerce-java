package com.ecommerceapp.users.core.port.outbound.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.ecommerceapp.users.core.domain.entities.User;

public interface UserRepository {
    void save(User user);

    Optional<User> findUserById(UUID id);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByPhoneNumber(String phoneNumber);

    List<User> findUserByIdList(List<UUID> ids);
}
