package com.ecommerceapp.users.core.events;

import com.ecommerceapp.libs.events.users.UserRegisterEvent;

public interface AuthEventPublisher {
    void publishUserRegisterEvent(UserRegisterEvent event);
}