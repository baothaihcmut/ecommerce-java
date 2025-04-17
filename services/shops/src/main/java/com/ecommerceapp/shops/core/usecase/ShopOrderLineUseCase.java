package com.ecommerceapp.shops.core.usecase;

import org.springframework.stereotype.Service;

import com.ecommerceapp.shops.core.port.inbound.handlers.ShopOrderLineHandler;
import com.ecommerceapp.shops.core.port.inbound.queries.GetShopOrderLineQuery;
import com.ecommerceapp.shops.core.port.inbound.results.GetOrderLinesOfShopResult;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShopOrderLineUseCase implements ShopOrderLineHandler {

    @Override
    public GetOrderLinesOfShopResult getOrderLineOfShop(GetShopOrderLineQuery query) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOrderLineOfShop'");
    }

}
