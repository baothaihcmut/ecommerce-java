package com.ecommerceapp.users.adapter.transport.rest.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDTO {

    @Email(message = "email invalid")
    @NotEmpty(message = "email is required")
    private String email;

    @NotEmpty(message = "password is required")
    private String password;

    @NotEmpty(message = "first name is required")
    private String firstName;

    @NotEmpty(message = "last name is required")
    private String lastName;

    @NotEmpty(message = "phone number is required")
    private String phoneNumber;
}
