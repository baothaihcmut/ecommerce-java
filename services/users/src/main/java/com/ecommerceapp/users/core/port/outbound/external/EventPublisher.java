package com.ecommerceapp.users.core.port.outbound.external;

import com.ecommerceapp.libs.events.users.UserRegisterEvent;

public interface EventPublisher {
    void publishUserRegisterEvent(UserRegisterEvent event);
}