package com.ecommerceapp.users.core.port.inbound.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpCommand {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
