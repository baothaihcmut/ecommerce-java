package com.ecommerceapp.shops.core.port.inbound.handlers;

import com.ecommerceapp.shops.core.port.inbound.commands.CreateShopCommand;
import com.ecommerceapp.shops.core.port.inbound.commands.FollowShopCommand;
import com.ecommerceapp.shops.core.port.inbound.commands.UnFollowShopCommand;
import com.ecommerceapp.shops.core.port.inbound.commands.UpdateShopCommand;
import com.ecommerceapp.shops.core.port.inbound.queries.GetShopByIdQuery;
import com.ecommerceapp.shops.core.port.inbound.results.CreateShopResult;
import com.ecommerceapp.shops.core.port.inbound.results.FollowShopResult;
import com.ecommerceapp.shops.core.port.inbound.results.GetShopByIdResult;
import com.ecommerceapp.shops.core.port.inbound.results.UnFollowShopResult;
import com.ecommerceapp.shops.core.port.inbound.results.UpdateShopResult;

public interface ShopHandler {
    // command
    CreateShopResult createShop(CreateShopCommand command);

    UpdateShopResult updateShop(UpdateShopCommand command);

    FollowShopResult followShop(FollowShopCommand followShopCommand);

    UnFollowShopResult unFollowShop(UnFollowShopCommand unFollowShopCommand);

    // query
    GetShopByIdResult getShopById(GetShopByIdQuery query);

}
