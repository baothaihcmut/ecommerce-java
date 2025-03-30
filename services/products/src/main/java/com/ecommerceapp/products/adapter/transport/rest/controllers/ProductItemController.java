package com.ecommerceapp.products.adapter.transport.rest.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerceapp.libs.response.AppResponse;
import com.ecommerceapp.products.adapter.transport.rest.dtos.request.CreateProductItemRequestDTO;
import com.ecommerceapp.products.adapter.transport.rest.mapper.ProductItemMapper;
import com.ecommerceapp.products.core.port.inbound.results.CreateProductItemResult;
import com.ecommerceapp.products.core.usecase.ProductItemUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/product-items")
@RequiredArgsConstructor
public class ProductItemController {
    private final ProductItemUseCase productItemUseCase;
    private final ProductItemMapper productItemMapper;

    @PostMapping("/add")
    public ResponseEntity<AppResponse> addProductItem(@RequestBody @Valid CreateProductItemRequestDTO dto) {
        CreateProductItemResult result = productItemUseCase
                .createProductItem(productItemMapper.toCreateProductItemCommand(dto));
        return AppResponse.initResponse(
                HttpStatus.CREATED,
                true,
                "create product item success",
                this.productItemMapper.toCreateProductItemResponse(result));
    }
}
