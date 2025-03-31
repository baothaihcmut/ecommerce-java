package com.ecommerceapp.shops.core.handlers;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerceapp.libs.events.products.ProductAdditionEvent;
import com.ecommerceapp.libs.exception.AppException;
import com.ecommerceapp.shops.core.domain.entities.Shop;
import com.ecommerceapp.shops.core.exception.ErrorCode;
import com.ecommerceapp.shops.core.port.inbound.events.ShopEventHandler;
import com.ecommerceapp.shops.core.port.outbound.repositories.ShopRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor

public class ShopEventHandlerImpl implements ShopEventHandler {
    private final ShopRepository shopRepository;

    @Override
    @Transactional
    public void handleAddProduct(ProductAdditionEvent event) {
        Shop shop = this.shopRepository.findShopById(
                new ObjectId(event.getShopId())).orElseThrow(() -> new AppException(ErrorCode.SHOP_NOT_EXIST));
        shop.addProducts(1);
        shopRepository.save(shop);

    }

}
