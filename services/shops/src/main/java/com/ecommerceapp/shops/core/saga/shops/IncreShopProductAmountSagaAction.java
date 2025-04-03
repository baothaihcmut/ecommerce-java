package com.ecommerceapp.shops.core.saga.shops;

import com.ecommerceapp.libs.saga.SagaAction;
import com.ecommerceapp.shops.core.domain.entities.Shop;
import com.ecommerceapp.shops.core.port.outbound.repositories.ShopRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IncreShopProductAmountSagaAction implements SagaAction<Void> {
    private final ShopRepository shopRepository;
    private final Shop shop;
    private final Integer numOfProducts;

    @Override
    public Void execute() {
        shop.addProducts(numOfProducts);
        shopRepository.save(shop);
        return null;
    }

    @Override
    public void abort() {
    }
}
