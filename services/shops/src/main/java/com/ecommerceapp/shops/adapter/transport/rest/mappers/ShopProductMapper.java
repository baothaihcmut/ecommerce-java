package com.ecommerceapp.shops.adapter.transport.rest.mappers;

import org.mapstruct.Mapper;

import com.ecommerceapp.shops.adapter.transport.rest.dtos.request.CreateProductRequestDTO;
import com.ecommerceapp.shops.adapter.transport.rest.dtos.response.CreateProductResponseDTO;
import com.ecommerceapp.shops.core.port.inbound.commands.CreateProductCommand;
import com.ecommerceapp.shops.core.port.inbound.results.CreateProductResult;

@Mapper(componentModel = "spring")
public interface ShopProductMapper {

    CreateProductCommand toCreateProductCommand(CreateProductRequestDTO dto, String shopId);

    CreateProductResponseDTO toCreateProductResponseDTO(CreateProductResult result);
}
