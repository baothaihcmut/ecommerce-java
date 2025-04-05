package com.ecommerceapp.shops.adapter.transport.rest.mappers;

import org.mapstruct.Mapper;

import com.ecommerceapp.shops.adapter.transport.rest.dtos.response.AddShopFollowerResponseDTO;
import com.ecommerceapp.shops.adapter.transport.rest.dtos.response.GetFollowersOfShopResponseDTO;
import com.ecommerceapp.shops.adapter.transport.rest.dtos.response.ShopFollowerResponseDTO;
import com.ecommerceapp.shops.core.port.inbound.results.FollowShopResult;
import com.ecommerceapp.shops.core.port.inbound.results.GetFollowersOfShopResult;
import com.ecommerceapp.shops.core.port.inbound.results.ShopFollowerResult;

@Mapper(componentModel = "spring")
public interface ShopFollowerMapper {
    ShopFollowerResponseDTO map(ShopFollowerResult result);

    AddShopFollowerResponseDTO toShopFollowerResponseDTO(FollowShopResult result);

    GetFollowersOfShopResponseDTO toGetFollowersOfShopResponseDTO(GetFollowersOfShopResult result);
}
