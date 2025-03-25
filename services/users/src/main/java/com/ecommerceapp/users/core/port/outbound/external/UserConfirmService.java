package com.ecommerceapp.users.core.port.outbound.external;

import java.util.Optional;

import com.ecommerceapp.users.core.domain.entities.User;

public interface UserConfirmService {
    Optional<User> getUser(String code) throws Exception;
    String storeUser(User user) throws Exception;
    boolean isUserPendingConfirm(String email) throws Exception;
}
