package com.ecommerceapp.users.core.port.inbound.handlers;

import com.ecommerceapp.users.core.port.inbound.commands.LoginCommand;
import com.ecommerceapp.users.core.port.inbound.commands.SignUpCommand;
import com.ecommerceapp.users.core.port.inbound.results.LoginResult;
import com.ecommerceapp.users.core.port.inbound.results.SignUpResult;

public interface AuthHandler {
    LoginResult logIn(LoginCommand command) throws Exception;
    SignUpResult signUp(SignUpCommand command) throws Exception;
}
