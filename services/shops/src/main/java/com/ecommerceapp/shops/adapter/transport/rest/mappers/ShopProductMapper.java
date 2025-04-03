package com.ecommerceapp.shops.adapter.transport.rest.mappers;

import org.mapstruct.Mapper;

import com.ecommerceapp.shops.adapter.transport.rest.dtos.request.CreateProductRequestDTO;
import com.ecommerceapp.shops.adapter.transport.rest.dtos.response.CreateProductResponseDTO;
import com.ecommerceapp.shops.adapter.transport.rest.dtos.response.GetProductsOfShopResponseDTO;
import com.ecommerceapp.shops.adapter.transport.rest.dtos.response.ProductWithThumbnailResponseDTO;
import com.ecommerceapp.shops.core.port.inbound.commands.CreateProductCommand;
import com.ecommerceapp.shops.core.port.inbound.results.CreateProductResult;
import com.ecommerceapp.shops.core.port.inbound.results.GetProductsOfShopResult;
import com.ecommerceapp.shops.core.port.inbound.results.ProductWithThumbnailResult;

@Mapper(componentModel = "spring")
public interface ShopProductMapper {

    CreateProductCommand toCreateProductCommand(CreateProductRequestDTO dto, String shopId);

    CreateProductResponseDTO toCreateProductResponseDTO(CreateProductResult result);

    ProductWithThumbnailResponseDTO map(ProductWithThumbnailResult result);

    GetProductsOfShopResponseDTO toGetProductsOfShopResponseDTO(GetProductsOfShopResult result);
}
