package com.ecommerceapp.users.core.port.inbound.handlers;

import com.ecommerceapp.users.core.port.inbound.queries.GetAddressByIdQuery;
import com.ecommerceapp.users.core.port.inbound.results.GetAddressByIdResult;

public interface UserAddressHandler {
    GetAddressByIdResult getAddressById(GetAddressByIdQuery query);
}
