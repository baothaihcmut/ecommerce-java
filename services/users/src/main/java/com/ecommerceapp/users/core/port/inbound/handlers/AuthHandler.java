package com.ecommerceapp.users.core.port.inbound.handlers;

import com.ecommerceapp.users.core.port.inbound.commands.ConfirmSignUpCommand;
import com.ecommerceapp.users.core.port.inbound.commands.LogInCommand;
import com.ecommerceapp.users.core.port.inbound.commands.SignUpCommand;
import com.ecommerceapp.users.core.port.inbound.results.ConfirmSignUpResult;
import com.ecommerceapp.users.core.port.inbound.results.LogInResult;
import com.ecommerceapp.users.core.port.inbound.results.SignUpResult;

public interface AuthHandler {
    LogInResult logIn(LogInCommand command) throws Exception;

    SignUpResult signUp(SignUpCommand command) throws Exception;

    ConfirmSignUpResult confirmSignUp(ConfirmSignUpCommand command) throws Exception;
}
