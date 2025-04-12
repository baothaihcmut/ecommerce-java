package com.ecommerceapp.products.adapter.transport.rest.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerceapp.libs.response.AppResponse;
import com.ecommerceapp.products.adapter.transport.rest.dtos.request.AddProductItemRequestDTO;
import com.ecommerceapp.products.adapter.transport.rest.dtos.response.AddProductItemResponseDTO;
import com.ecommerceapp.products.adapter.transport.rest.mappers.ProductItemMapper;
import com.ecommerceapp.products.core.port.inbound.handlers.ProductItemHandler;
import com.ecommerceapp.products.core.port.inbound.results.CreateProductItemResult;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products/{id}/items")
@RequiredArgsConstructor
public class ProductItemController {
    private final ProductItemHandler productItemHandler;
    private final ProductItemMapper productItemMapper;

    @PostMapping("/add")
    public AppResponse<AddProductItemResponseDTO> addProductItem(
            @PathVariable String id,
            @RequestBody @Valid AddProductItemRequestDTO dto) {
        CreateProductItemResult result = productItemHandler.createProductItem(
                productItemMapper.toCreateProductItemCommand(dto, id));
        return AppResponse.initResponse(
                true,
                "add product item success",
                productItemMapper.toAddProductItemResponseDTO(result));
    }
}
