package com.ecommerceapp.shops.adapter.transport.rest.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.ecommerceapp.shops.adapter.transport.rest.dtos.request.AddShopCategoryRequestDTO;
import com.ecommerceapp.shops.adapter.transport.rest.dtos.response.AddShopCategoryResponseDTO;
import com.ecommerceapp.shops.core.port.inbound.commands.CreateShopCategoryCommand;
import com.ecommerceapp.shops.core.port.inbound.results.CreateShopCategoryResult;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShopCategoryMapper {
    CreateShopCategoryCommand toCreateShopCategoryCommand(AddShopCategoryRequestDTO requestDTO, String shopId,
            String parentShopCategoryId);

    AddShopCategoryResponseDTO toAddShopCategoryResponseDTO(CreateShopCategoryResult result);
}
