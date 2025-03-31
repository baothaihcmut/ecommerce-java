package com.ecommerceapp.users.core.usecase;

import org.springframework.stereotype.Service;

import com.ecommerceapp.libs.events.users.UserRegisterEvent;
import com.ecommerceapp.libs.exception.AppException;
import com.ecommerceapp.users.core.domain.entities.User;
import com.ecommerceapp.users.core.events.AuthEventPublisher;
import com.ecommerceapp.users.core.exception.ErrorCode;
import com.ecommerceapp.users.core.port.inbound.commands.ConfirmSignUpCommand;
import com.ecommerceapp.users.core.port.inbound.commands.LogInCommand;
import com.ecommerceapp.users.core.port.inbound.commands.SignUpCommand;
import com.ecommerceapp.users.core.port.inbound.handlers.AuthHandler;
import com.ecommerceapp.users.core.port.inbound.results.ConfirmSignUpResult;
import com.ecommerceapp.users.core.port.inbound.results.LogInResult;
import com.ecommerceapp.users.core.port.inbound.results.SignUpResult;
import com.ecommerceapp.users.core.port.outbound.repositories.UserRepository;
import com.ecommerceapp.users.core.services.AuthUtilService;
import com.ecommerceapp.users.core.services.AuthUtilService.AccessTokenPayload;
import com.ecommerceapp.users.core.services.UserConfirmService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthUseCase implements AuthHandler {
    private final UserRepository userRepository;
    private final UserConfirmService userConfirmService;
    private final AuthEventPublisher eventPublisher;
    private final AuthUtilService authUtilService;

    @Override
    public LogInResult logIn(LogInCommand command) throws Exception {
        // find user from db
        User user = this.userRepository.findUserByEmail(command.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.BAD_CREDENTIALS_EXCEPTION));
        // validate password
        if (!user.validatePassword(command.getPassword())) {
            throw new AppException(ErrorCode.BAD_CREDENTIALS_EXCEPTION);
        }
        // gen token
        String accessToken = this.authUtilService.genAccessToken(
                new AccessTokenPayload(user.getId().toString(), user.isShopOwnerActive()));
        String refreshToken = this.authUtilService.genRefreshToken(user.getId());
        return LogInResult.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public SignUpResult signUp(SignUpCommand command) throws Exception {
        // check user is pending for sign up
        if (this.userConfirmService.isUserPendingConfirm(command.getEmail())) {
            throw new AppException(ErrorCode.USER_PENDING_VERFICATION_SIGN_UP);
        }
        // check mail exist
        if (this.userRepository.findUserByEmail(command.getEmail()).orElse(null) != null) {
            throw new AppException(ErrorCode.EMAIL_EXIST);
        }
        // check phone number exist
        if (this.userRepository.findUserByPhoneNumber(command.getPhoneNumber()).orElse(null) != null) {
            throw new AppException(ErrorCode.PHONE_NUMBER_EXIST);
        }

        // init user
        User newUser = new User(
                command.getEmail(),
                command.getPassword(),
                command.getFirstName(),
                command.getLastName(),
                command.getPhoneNumber());
        // store user to cache
        String confirmUrl = this.userConfirmService.storeUser(newUser);
        // publish event
        this.eventPublisher.publishUserRegisterEvent(
                UserRegisterEvent.builder()
                        .id(newUser.getId())
                        .email(newUser.getEmail())
                        .firstName(newUser.getFirstName())
                        .lastName(newUser.getLastName())
                        .phoneNumber(newUser.getPhoneNumber())
                        .comfirmUrl(confirmUrl)
                        .build());
        return SignUpResult.builder().build();
    }

    @Override
    @Transactional
    public ConfirmSignUpResult confirmSignUp(ConfirmSignUpCommand command) throws Exception {
        // get user from cache
        User user = this.userConfirmService.getUser(command.getCode())
                .orElseThrow(() -> new AppException(ErrorCode.WRONG_VERIFICATION_CODE));
        // store to db
        this.userRepository.save(user);
        return new ConfirmSignUpResult();
    }

}
