package com.ecommerceapp.shops.core.usecase;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerceapp.libs.exception.AppException;
import com.ecommerceapp.libs.security.SecurityUtil;
import com.ecommerceapp.libs.security.SecurityUtil.UserContext;
import com.ecommerceapp.shops.core.domain.entities.Shop;
import com.ecommerceapp.shops.core.exception.ErrorCode;
import com.ecommerceapp.shops.core.port.inbound.commands.CreateShopCommand;

import com.ecommerceapp.shops.core.port.inbound.commands.UpdateShopCommand;
import com.ecommerceapp.shops.core.port.inbound.handlers.ShopHandler;
import com.ecommerceapp.shops.core.port.inbound.queries.GetShopByIdQuery;
import com.ecommerceapp.shops.core.port.inbound.results.CreateShopResult;
import com.ecommerceapp.shops.core.port.inbound.results.GetShopByIdResult;
import com.ecommerceapp.shops.core.port.inbound.results.ShopResult;
import com.ecommerceapp.shops.core.port.inbound.results.UpdateShopResult;
import com.ecommerceapp.shops.core.port.outbound.repositories.ShopRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShopUseCase implements ShopHandler {
        private final ShopRepository shopRepository;

        @Override
        @Transactional
        public CreateShopResult createShop(CreateShopCommand command) {
                UserContext userContext = SecurityUtil.getUserContext();

                if (!userContext.isShopOwnerActive()) {
                        throw new AppException(ErrorCode.USER_NOT_SHOP_OWNER_ACTIVE);
                }

                // create shop
                Shop shop = new Shop(
                                userContext.userId(),
                                command.name(),
                                command.description(),
                                command.shopAddress(),
                                command.shopStreet(),
                                command.shopWard(),
                                command.shopDistrict(),
                                command.shopProvince());
                shopRepository.save(shop);
                return CreateShopResult.builder()
                                .shop(ShopResult.toShopResult(shop))
                                .build();
        }

        @Override
        public GetShopByIdResult getShopById(GetShopByIdQuery query) {
                System.out.println(query.id());
                Shop shop = shopRepository.findShopById(new ObjectId(query.id()))
                                .orElseThrow(() -> new AppException(ErrorCode.SHOP_NOT_EXIST));
                return GetShopByIdResult.builder()
                                .shop(ShopResult.toShopResult(shop))
                                .build();
        }

        @Override
        @Transactional
        public UpdateShopResult updateShop(UpdateShopCommand command) {
                UserContext userContext = SecurityUtil.getUserContext();
                if (!userContext.isShopOwnerActive()) {
                        throw new AppException(ErrorCode.USER_NOT_SHOP_OWNER_ACTIVE);
                }
                Shop shop = shopRepository.findShopById(new ObjectId(command.getId()))
                                .orElseThrow(() -> new AppException(ErrorCode.SHOP_NOT_EXIST));
                if (!shop.getOwnerId().equals(userContext.userId())) {
                        throw new AppException(ErrorCode.SHOP_NOT_BELONG_TO_USER);
                }
                if (command.getName() != null) {
                        shop.setName(command.getName());
                }
                if (command.getDescription() != null) {
                        shop.setDescription(command.getDescription());
                }
                shopRepository.save(shop);
                return UpdateShopResult.builder()
                                .shop(ShopResult.toShopResult(shop))
                                .build();
        }

}
