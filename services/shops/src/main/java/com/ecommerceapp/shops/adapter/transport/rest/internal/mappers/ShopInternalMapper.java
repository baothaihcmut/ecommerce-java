package com.ecommerceapp.shops.adapter.transport.rest.internal.mappers;

import org.mapstruct.Mapper;

import com.ecommerceapp.libs.dtos.shop.response.GetShopByIdResponseDTO;
import com.ecommerceapp.libs.dtos.shop.response.ShopResponseDTO;
import com.ecommerceapp.shops.core.port.inbound.results.GetShopByIdResult;
import com.ecommerceapp.shops.core.port.inbound.results.ShopResult;

@Mapper(componentModel = "spring")
public interface ShopInternalMapper {
    ShopResponseDTO toShopResponseDTO(ShopResult result);

    GetShopByIdResponseDTO toGetShopByIdResponse(GetShopByIdResult result);
}
