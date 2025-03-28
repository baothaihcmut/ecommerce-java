package com.ecommerceapp.products.adapter.transport.rest.mapper;

import org.mapstruct.Mapper;

import com.ecommerceapp.products.adapter.transport.rest.dtos.request.CreateProductRequestDTO;
import com.ecommerceapp.products.adapter.transport.rest.dtos.response.CreateProductResponseDTO;
import com.ecommerceapp.products.adapter.transport.rest.dtos.response.ProductResponseDTO;
import com.ecommerceapp.products.adapter.transport.rest.dtos.response.VariationReponseDTO;
import com.ecommerceapp.products.core.port.inbound.commands.CreateProductCommand;
import com.ecommerceapp.products.core.port.inbound.results.CreateProductResult;
import com.ecommerceapp.products.core.port.inbound.results.ProductResult;
import com.ecommerceapp.products.core.port.inbound.results.VariationResult;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    CreateProductCommand toCreateProductCommand(CreateProductRequestDTO dto);

    ProductResponseDTO toProductResponseDTO(ProductResult result);

    VariationReponseDTO toVariationResponseDTO(VariationResult result);

    CreateProductResponseDTO.UploadImageInfo toUploadImageInfoResponse(CreateProductResult.UploadImageInfo result);

    CreateProductResponseDTO toCreateProductResponseDTO(CreateProductResult result);

}
