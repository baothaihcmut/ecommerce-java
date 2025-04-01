package com.ecommerceapp.shops.core.port.inbound.handlers;

import com.ecommerceapp.shops.core.port.inbound.commands.FollowShopCommand;
import com.ecommerceapp.shops.core.port.inbound.commands.UnFollowShopCommand;
import com.ecommerceapp.shops.core.port.inbound.results.FollowShopResult;
import com.ecommerceapp.shops.core.port.inbound.results.UnFollowShopResult;

public interface ShopFollowerHandler {
    FollowShopResult followShop(FollowShopCommand followShopCommand);

    UnFollowShopResult unFollowShop(UnFollowShopCommand unFollowShopCommand);

}
