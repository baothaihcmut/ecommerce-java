package com.ecommerceapp.shops.core.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerceapp.libs.exception.AppException;
import com.ecommerceapp.libs.security.SecurityUtil;
import com.ecommerceapp.libs.security.SecurityUtil.UserContext;
import com.ecommerceapp.shops.core.domain.entities.Shop;
import com.ecommerceapp.shops.core.domain.entities.ShopFollower;
import com.ecommerceapp.shops.core.exception.ErrorCode;
import com.ecommerceapp.shops.core.port.inbound.commands.FollowShopCommand;
import com.ecommerceapp.shops.core.port.inbound.commands.UnFollowShopCommand;
import com.ecommerceapp.shops.core.port.inbound.handlers.ShopFollowerHandler;
import com.ecommerceapp.shops.core.port.inbound.results.FollowShopResult;
import com.ecommerceapp.shops.core.port.inbound.results.ShopFollowerResult;
import com.ecommerceapp.shops.core.port.inbound.results.UnFollowShopResult;
import com.ecommerceapp.shops.core.port.outbound.repositories.ShopFollowerRepository;
import com.ecommerceapp.shops.core.port.outbound.repositories.ShopRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShopFollowerUseCase implements ShopFollowerHandler {
        private final ShopFollowerRepository shopFollowerRepository;
        private final ShopRepository shopRepository;

        @Override
        @Transactional
        public FollowShopResult followShop(FollowShopCommand followShopCommand) {
                UserContext userContext = SecurityUtil.getUserContext();
                Shop shop = shopRepository.findShopById(followShopCommand.getShopId())
                                .orElseThrow(() -> new AppException(ErrorCode.SHOP_NOT_EXIST));
                ShopFollower shopFollower = new ShopFollower(shop.getId(), userContext.userId());
                shop.addFollower();
                shopFollowerRepository.save(shopFollower);
                shopRepository.save(shop);
                return FollowShopResult.builder()
                                .shopFollower(ShopFollowerResult.toSopFollowerResult(shopFollower))
                                .build();

        }

        @Override
        @Transactional
        public UnFollowShopResult unFollowShop(UnFollowShopCommand unFollowShopCommand) {
                UserContext userContext = SecurityUtil.getUserContext();
                Shop shop = shopRepository.findShopById(unFollowShopCommand.getShopId())
                                .orElseThrow(() -> new AppException(ErrorCode.SHOP_NOT_EXIST));
                ShopFollower shopFollower = shopFollowerRepository
                                .findByShopIdAndUserId(shop.getId(), userContext.userId())
                                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOLLOW_SHOP));
                shop.deleteFollower();
                shopRepository.save(shop);
                shopFollowerRepository.delete(shopFollower);
                return new UnFollowShopResult();
        }
}
