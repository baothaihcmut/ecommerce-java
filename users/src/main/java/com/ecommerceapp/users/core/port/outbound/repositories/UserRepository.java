package com.ecommerceapp.users.core.port.outbound.repositories;

import java.util.Optional;

import com.ecommerceapp.users.core.domain.entities.User;



public interface UserRepository {
    User save(User user);
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByPhoneNumber(String phoneNumber);
}
