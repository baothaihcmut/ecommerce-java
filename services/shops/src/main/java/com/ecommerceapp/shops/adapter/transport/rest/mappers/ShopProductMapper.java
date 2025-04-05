package com.ecommerceapp.shops.adapter.transport.rest.mappers;

import org.mapstruct.Mapper;

import com.ecommerceapp.shops.adapter.transport.rest.dtos.request.AddShopProductRequestDTO;
import com.ecommerceapp.shops.adapter.transport.rest.dtos.response.AddShopProductResponseDTO;
import com.ecommerceapp.shops.adapter.transport.rest.dtos.response.GetProductsOfShopResponseDTO;
import com.ecommerceapp.shops.adapter.transport.rest.dtos.response.ProductWithThumbnailResponseDTO;
import com.ecommerceapp.shops.core.port.inbound.commands.CreateProductCommand;
import com.ecommerceapp.shops.core.port.inbound.results.CreateProductResult;
import com.ecommerceapp.shops.core.port.inbound.results.GetProductsOfShopResult;
import com.ecommerceapp.shops.core.port.inbound.results.ProductWithThumbnailResult;

@Mapper(componentModel = "spring")
public interface ShopProductMapper {

    CreateProductCommand toCreateProductCommand(AddShopProductRequestDTO dto, String shopId);

    AddShopProductResponseDTO toCreateProductResponseDTO(CreateProductResult result);

    ProductWithThumbnailResponseDTO map(ProductWithThumbnailResult result);

    GetProductsOfShopResponseDTO toGetProductsOfShopResponseDTO(GetProductsOfShopResult result);
}
