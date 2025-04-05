package com.ecommerceapp.shops.core.port.inbound.results;

import com.ecommerceapp.shops.core.domain.entities.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserResult {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Boolean isShopOwnerActive;

    public static UserResult toUserResult(User user) {
        return UserResult.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .isShopOwnerActive(user.getIsShopOwnerActive())
                .build();
    }
}
