package com.ecommerceapp.mail.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserRegister {
    private String email;
    private String firstName;
    private String lastName;
    private String confirmUrl;
}
