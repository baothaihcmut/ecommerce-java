package com.ecommerceapp.shops.core.port.outbound.clients;

import java.util.List;

import com.ecommerceapp.shops.core.domain.entities.User;

public interface UserClient {
    List<User> getUserByIdList(List<String> ids);
}
