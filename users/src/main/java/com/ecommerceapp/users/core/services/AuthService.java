package com.ecommerceapp.users.core.services;

import org.springframework.stereotype.Service;

import com.ecommerceapp.users.core.port.inbound.commands.LoginCommand;
import com.ecommerceapp.users.core.port.inbound.handlers.AuthHandler;
import com.ecommerceapp.users.core.port.inbound.results.LoginResult;

@Service
public class AuthService implements AuthHandler {

    @Override
    public LoginResult LogIn(LoginCommand command) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'LogIn'");
    }
    
}
