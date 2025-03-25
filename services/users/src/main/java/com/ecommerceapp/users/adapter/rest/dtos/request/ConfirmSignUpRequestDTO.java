package com.ecommerceapp.users.adapter.rest.dtos.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmSignUpRequestDTO {
    @NotEmpty(message = "code is required")
    private String code;
}
