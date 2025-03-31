package com.ecommerceapp.shops.adapter.transport.rest.mappers;

import org.mapstruct.Mapper;

import com.ecommerceapp.shops.adapter.transport.rest.dtos.request.CreateShopRequestDTO;
import com.ecommerceapp.shops.adapter.transport.rest.dtos.response.CreateShopResponseDTO;
import com.ecommerceapp.shops.adapter.transport.rest.dtos.response.ShopResponseDTO;
import com.ecommerceapp.shops.core.port.inbound.commands.CreateShopCommand;
import com.ecommerceapp.shops.core.port.inbound.results.CreateShopResult;
import com.ecommerceapp.shops.core.port.inbound.results.ShopResult;

@Mapper(componentModel = "spring")
public interface ShopMapper {
    CreateShopCommand toCreateShopCommand(CreateShopRequestDTO dto);

    ShopResponseDTO toShopResponseDTO(ShopResult result);

    CreateShopResponseDTO toCreateShopResponseDTO(CreateShopResult result);
}
