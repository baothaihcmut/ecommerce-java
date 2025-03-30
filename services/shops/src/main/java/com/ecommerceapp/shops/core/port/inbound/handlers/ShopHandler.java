package com.ecommerceapp.shops.core.port.inbound.handlers;

import com.ecommerceapp.shops.core.port.inbound.commands.CreateShopCommand;
import com.ecommerceapp.shops.core.port.inbound.queries.GetShopByIdQuery;
import com.ecommerceapp.shops.core.port.inbound.results.CreateShopResult;
import com.ecommerceapp.shops.core.port.inbound.results.GetShopByIdResult;

public interface ShopHandler {
    // command
    CreateShopResult createShop(CreateShopCommand command);

    // query
    GetShopByIdResult getShopById(GetShopByIdQuery query);

}
