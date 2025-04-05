package com.ecommerceapp.users.core.port.inbound.handlers;

import com.ecommerceapp.users.core.port.inbound.queries.GetUserByIdQuery;
import com.ecommerceapp.users.core.port.inbound.queries.GetUsersByIdListQuery;
import com.ecommerceapp.users.core.port.inbound.results.GetUserByIdResult;
import com.ecommerceapp.users.core.port.inbound.results.GetUsersByIdListResult;

public interface UserHandler {
    GetUserByIdResult getUserById(GetUserByIdQuery query);

    GetUsersByIdListResult getUserByIdList(GetUsersByIdListQuery query);
}
