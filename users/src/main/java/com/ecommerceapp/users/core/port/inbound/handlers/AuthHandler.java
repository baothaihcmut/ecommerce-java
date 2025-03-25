package com.ecommerceapp.users.core.port.inbound.handlers;

import com.ecommerceapp.users.core.port.inbound.commands.LoginCommand;
import com.ecommerceapp.users.core.port.inbound.results.LoginResult;

public interface AuthHandler {
    LoginResult LogIn(LoginCommand command);
}
