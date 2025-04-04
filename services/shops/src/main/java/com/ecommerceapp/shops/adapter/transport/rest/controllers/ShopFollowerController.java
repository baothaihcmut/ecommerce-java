package com.ecommerceapp.shops.adapter.transport.rest.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerceapp.libs.queries.PaginationQuery;
import com.ecommerceapp.libs.response.AppResponse;
import com.ecommerceapp.shops.adapter.transport.rest.mappers.ShopFollowerMapper;
import com.ecommerceapp.shops.core.port.inbound.commands.FollowShopCommand;
import com.ecommerceapp.shops.core.port.inbound.handlers.ShopFollowerHandler;
import com.ecommerceapp.shops.core.port.inbound.queries.GetFollowersOfShopQuery;
import com.ecommerceapp.shops.core.port.inbound.results.FollowShopResult;
import com.ecommerceapp.shops.core.port.inbound.results.GetFollowersOfShopResult;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/shops/{id}/followers")
@RequiredArgsConstructor
public class ShopFollowerController {
        private final ShopFollowerHandler shopFollowerHandler;
        private final ShopFollowerMapper shopFollowerMapper;

        @PostMapping("/add")
        public ResponseEntity<AppResponse> addShopFollower(
                        @PathVariable("id") String id) {
                FollowShopResult result = shopFollowerHandler.followShop(
                                FollowShopCommand.builder().shopId(id).build());
                return AppResponse.initResponse(
                                HttpStatus.CREATED,
                                true,
                                "follow shop success",
                                shopFollowerMapper.toShopFollowerResponseDTO(result));
        }

        @GetMapping("")
        public ResponseEntity<AppResponse> getShopFollowers(
                        @PathVariable("id") String shopId,
                        @RequestParam(required = false, defaultValue = "0") int offset,
                        @RequestParam(required = false, defaultValue = "10") int limit) {
                GetFollowersOfShopResult result = shopFollowerHandler.getFollowersOfShop(
                                GetFollowersOfShopQuery.builder()
                                                .shopId(shopId)
                                                .pagination(
                                                                PaginationQuery.builder()
                                                                                .offset(offset)
                                                                                .limit(limit)
                                                                                .build())
                                                .build());
                return AppResponse.initResponse(
                                HttpStatus.OK, true,
                                "get followers of shop success",
                                shopFollowerMapper.toGetFollowersOfShopResponseDTO(result));
        }

}
