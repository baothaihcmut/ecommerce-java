package com.ecommerceapp.users.core.port.inbound.results;

import com.ecommerceapp.users.core.domain.entities.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
                .id(user.getId().toString())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .isShopOwnerActive(user.isShopOwnerActive())
                .build();
    }
}
