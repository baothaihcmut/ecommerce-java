package com.ecommerceapp.users.core.port.inbound.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginCommand {
    private String email;
    private String password;
}
