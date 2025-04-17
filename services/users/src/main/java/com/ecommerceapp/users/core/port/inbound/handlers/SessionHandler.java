package com.ecommerceapp.users.core.port.inbound.handlers;

import com.ecommerceapp.users.core.port.inbound.commands.InitSessionOrderStatusCommand;
import com.ecommerceapp.users.core.port.inbound.results.InitSessionOrderStatusResult;

public interface SessionHandler {
    InitSessionOrderStatusResult initSessionOrderStatus(InitSessionOrderStatusCommand command);
}
