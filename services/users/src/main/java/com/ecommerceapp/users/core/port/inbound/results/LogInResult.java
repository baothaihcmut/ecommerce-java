package com.ecommerceapp.users.core.port.inbound.results;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogInResult {
    private String accessToken;
    private String refreshToken;
}
