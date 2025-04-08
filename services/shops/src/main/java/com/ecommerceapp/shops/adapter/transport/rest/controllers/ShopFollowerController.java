package com.ecommerceapp.shops.adapter.transport.rest.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerceapp.libs.queries.PaginationQuery;
import com.ecommerceapp.libs.response.AppResponse;
import com.ecommerceapp.shops.adapter.transport.rest.dtos.response.AddShopFollowerResponseDTO;
import com.ecommerceapp.shops.adapter.transport.rest.dtos.response.GetFollowersOfShopResponseDTO;
import com.ecommerceapp.shops.adapter.transport.rest.mappers.ShopFollowerMapper;
import com.ecommerceapp.shops.core.port.inbound.commands.FollowShopCommand;
import com.ecommerceapp.shops.core.port.inbound.handlers.ShopFollowerHandler;
import com.ecommerceapp.shops.core.port.inbound.queries.GetFollowersOfShopQuery;
import com.ecommerceapp.shops.core.port.inbound.results.FollowShopResult;
import com.ecommerceapp.shops.core.port.inbound.results.GetFollowersOfShopResult;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("/shops/{id}/followers")
@RequiredArgsConstructor
@Tag(name = "Shop Follower", description = "shop follower api")
public class ShopFollowerController {
    private final ShopFollowerHandler shopFollowerHandler;
    private final ShopFollowerMapper shopFollowerMapper;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public AppResponse<AddShopFollowerResponseDTO> addShopFollower(
            @PathVariable String id) {
        FollowShopResult result = shopFollowerHandler.followShop(
                FollowShopCommand.builder().shopId(id).build());
        return AppResponse.initResponse(
                true,
                "follow shop success",
                shopFollowerMapper.toShopFollowerResponseDTO(result));
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public AppResponse<GetFollowersOfShopResponseDTO> getShopFollowers(
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
                true,
                "get followers of shop success",
                shopFollowerMapper.toGetFollowersOfShopResponseDTO(result));
    }

}
