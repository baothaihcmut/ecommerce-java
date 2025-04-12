package com.ecommerceapp.products.adapter.transport.rest.mappers;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;

import com.ecommerceapp.products.adapter.transport.rest.dtos.request.AddProductItemRequestDTO;
import com.ecommerceapp.products.adapter.transport.rest.dtos.response.AddProductItemResponseDTO;
import com.ecommerceapp.products.adapter.transport.rest.dtos.response.ProductItemResponseDTO;
import com.ecommerceapp.products.core.port.inbound.commands.CreateProductItemCommand;
import com.ecommerceapp.products.core.port.inbound.results.CreateProductItemResult;
import com.ecommerceapp.products.core.port.inbound.results.ProductItemResult;

@Mapper(componentModel = "spring", collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface ProductItemMapper {
    CreateProductItemCommand toCreateProductItemCommand(AddProductItemRequestDTO requestDTO, String productId);

    ProductItemResponseDTO map(ProductItemResult result);

    AddProductItemResponseDTO toAddProductItemResponseDTO(CreateProductItemResult result);
}
