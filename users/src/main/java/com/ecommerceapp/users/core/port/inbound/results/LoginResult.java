package com.ecommerceapp.users.core.port.inbound.results;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginResult {
    private String accessToken;
    private String refreshToken;
}
