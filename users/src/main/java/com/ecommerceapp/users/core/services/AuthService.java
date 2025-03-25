package com.ecommerceapp.users.core.services;

import org.springframework.stereotype.Service;

import com.ecommerceapp.libs.exception.AppException;
import com.ecommerceapp.users.core.domain.entities.User;
import com.ecommerceapp.users.core.exception.ErrorCode;
import com.ecommerceapp.users.core.port.inbound.commands.LoginCommand;
import com.ecommerceapp.users.core.port.inbound.commands.SignUpCommand;
import com.ecommerceapp.users.core.port.inbound.handlers.AuthHandler;
import com.ecommerceapp.users.core.port.inbound.results.LoginResult;
import com.ecommerceapp.users.core.port.inbound.results.SignUpResult;
import com.ecommerceapp.users.core.port.outbound.external.UserConfirmService;
import com.ecommerceapp.users.core.port.outbound.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthHandler {
    private final UserRepository userRepository;
    private final UserConfirmService userConfirmService;



    @Override
    public LoginResult logIn(LoginCommand command) throws Exception{
        throw new UnsupportedOperationException("Unimplemented method 'logIn'");
    }

    @Override
    public SignUpResult signUp(SignUpCommand command) throws Exception{
        //check user is pending for sign up
        if(this.userConfirmService.isUserPendingConfirm(command.getEmail())) {
            throw new AppException(ErrorCode.USER_PENDING_VERFICATION_SIGN_UP);
        }
        //check mail exist
        if(this.userRepository.findUserByEmail(command.getEmail()).orElse(null) != null) {
            throw new AppException(ErrorCode.EMAIL_EXIST);
        }
        //check phone number exist
        if(this.userRepository.findUserByPhoneNumber(command.getPhoneNumber()).orElse(null)!= null) {
            throw new AppException(ErrorCode.PHONE_NUMBER_EXIST);
        }
        
        //init user
        User newUser = new User(
            command.getEmail(),
            command.getPassword(),
            command.getFirstName(),
            command.getLastName(),
            command.getPhoneNumber()
        );
        //store user to cache
        String confirmUrl = this.userConfirmService.storeUser(newUser);
        System.out.println(confirmUrl);
        return SignUpResult.builder().build();

    }
    
}
