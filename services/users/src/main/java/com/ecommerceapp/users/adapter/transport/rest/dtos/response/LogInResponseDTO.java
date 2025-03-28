package com.ecommerceapp.users.adapter.transport.rest.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogInResponseDTO {
    private String accessToken;
    private String refreshToken;
}
