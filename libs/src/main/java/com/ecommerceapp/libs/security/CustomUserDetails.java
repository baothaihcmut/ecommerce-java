package com.ecommerceapp.libs.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomUserDetails implements UserDetails {
    private String userId;
    private Boolean isShopOwnerActive;
    private String token;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(String userId, Boolean isShopOwnerActive, String token) {
        this.userId = userId;
        this.isShopOwnerActive = isShopOwnerActive;
        this.token = token;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }

}
