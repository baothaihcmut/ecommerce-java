package com.ecommerceapp.libs.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserContext {
    private String userId;
    private Boolean isShopOwnerActive;
}
