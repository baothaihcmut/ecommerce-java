package com.ecommerceapp.products.adapter.transport.rest.mapper;

import org.mapstruct.Mapper;

import com.ecommerceapp.products.adapter.transport.rest.dtos.request.CreateProductItemRequestDTO;
import com.ecommerceapp.products.adapter.transport.rest.dtos.response.CreateProductItemResponseDTO;
import com.ecommerceapp.products.adapter.transport.rest.dtos.response.ProductItemResponseDTO;
import com.ecommerceapp.products.core.port.inbound.commands.CreateProductItemCommand;
import com.ecommerceapp.products.core.port.inbound.results.CreateProductItemResult;
import com.ecommerceapp.products.core.port.inbound.results.ProductItemResult;

@Mapper(componentModel = "spring")
public interface ProductItemMapper {
    CreateProductItemCommand.ProductItemVariaionValue toProductItemVariationValue(
            CreateProductItemCommand.ProductItemVariaionValue req);

    CreateProductItemCommand toCreateProductItemCommand(CreateProductItemRequestDTO requestDTO);

    ProductItemResponseDTO toProductItemResponseDTO(ProductItemResult result);

    CreateProductItemResponseDTO toCreateProductItemResponse(CreateProductItemResult result);
}
