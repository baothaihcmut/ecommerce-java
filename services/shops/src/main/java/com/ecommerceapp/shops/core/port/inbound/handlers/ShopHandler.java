package com.ecommerceapp.shops.core.port.inbound.handlers;

import com.ecommerceapp.shops.core.port.inbound.commands.CreateShopCommand;
import com.ecommerceapp.shops.core.port.inbound.commands.UpdateShopCommand;
import com.ecommerceapp.shops.core.port.inbound.queries.GetShopByIdQuery;
import com.ecommerceapp.shops.core.port.inbound.results.CreateShopResult;
import com.ecommerceapp.shops.core.port.inbound.results.GetShopByIdResult;
import com.ecommerceapp.shops.core.port.inbound.results.UpdateShopResult;

public interface ShopHandler {
    // command
    CreateShopResult createShop(CreateShopCommand command);

    UpdateShopResult updateShop(UpdateShopCommand command);

    // query
    GetShopByIdResult getShopById(GetShopByIdQuery query);

}
