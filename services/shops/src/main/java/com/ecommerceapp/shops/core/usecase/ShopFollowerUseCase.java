package com.ecommerceapp.shops.core.usecase;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerceapp.libs.exception.AppException;
import com.ecommerceapp.libs.response.PaginationResponse;
import com.ecommerceapp.libs.security.SecurityUtil;
import com.ecommerceapp.libs.security.SecurityUtil.UserContext;
import com.ecommerceapp.shops.core.domain.entities.Shop;
import com.ecommerceapp.shops.core.domain.entities.ShopFollower;
import com.ecommerceapp.shops.core.domain.entities.User;
import com.ecommerceapp.shops.core.exception.ErrorCode;
import com.ecommerceapp.shops.core.port.inbound.commands.FollowShopCommand;
import com.ecommerceapp.shops.core.port.inbound.commands.UnFollowShopCommand;
import com.ecommerceapp.shops.core.port.inbound.handlers.ShopFollowerHandler;
import com.ecommerceapp.shops.core.port.inbound.queries.GetFollowersOfShopQuery;
import com.ecommerceapp.shops.core.port.inbound.results.FollowShopResult;
import com.ecommerceapp.shops.core.port.inbound.results.GetFollowersOfShopResult;
import com.ecommerceapp.shops.core.port.inbound.results.ShopFollowerResult;
import com.ecommerceapp.shops.core.port.inbound.results.UnFollowShopResult;
import com.ecommerceapp.shops.core.port.inbound.results.UserResult;
import com.ecommerceapp.shops.core.port.outbound.clients.UserClient;
import com.ecommerceapp.shops.core.port.outbound.repositories.ShopFollowerRepository;
import com.ecommerceapp.shops.core.port.outbound.repositories.ShopRepository;
import com.ecommerceapp.shops.core.port.outbound.repositories.ShopFollowerRepository.FindShopFollowersByShopIdAndCountResult;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShopFollowerUseCase implements ShopFollowerHandler {
        private final ShopFollowerRepository shopFollowerRepository;
        private final UserClient userClient;
        private final ShopRepository shopRepository;

        @Override
        @Transactional
        public FollowShopResult followShop(FollowShopCommand followShopCommand) {
                UserContext userContext = SecurityUtil.getUserContext();
                Shop shop = shopRepository.findShopById(new ObjectId(followShopCommand.getShopId()))
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
                Shop shop = shopRepository.findShopById(new ObjectId(unFollowShopCommand.getShopId()))
                                .orElseThrow(() -> new AppException(ErrorCode.SHOP_NOT_EXIST));
                ShopFollower shopFollower = shopFollowerRepository
                                .findByShopIdAndUserId(shop.getId(), userContext.userId())
                                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOLLOW_SHOP));
                shop.deleteFollower();
                shopRepository.save(shop);
                shopFollowerRepository.delete(shopFollower);
                return new UnFollowShopResult();
        }

        @Override
        public GetFollowersOfShopResult getFollowersOfShop(GetFollowersOfShopQuery query) {
                FindShopFollowersByShopIdAndCountResult followers = shopFollowerRepository
                                .findShopFollowersByShopIdAndCount(
                                                new ObjectId(query.getShopId()),
                                                query.getPagination().getLimit(),
                                                query.getPagination().getOffset());
                // get all user info
                List<User> users = userClient.getUserByIdList(
                                followers.followers().stream().map(ShopFollower::getUserId).toList());

                return GetFollowersOfShopResult.builder()
                                .followers(
                                                users.stream().map(UserResult::toUserResult).toList())
                                .pagination(PaginationResponse.initPaginationResponse(
                                                query.getPagination().getOffset(),
                                                query.getPagination().getLimit(),
                                                Integer.valueOf((int) followers.count())))
                                .build();
        }

}
