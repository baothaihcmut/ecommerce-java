package com.ecommerceapp.products.adapter.transport.rest.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerceapp.libs.response.AppResponse;
import com.ecommerceapp.products.adapter.transport.rest.dtos.request.CreateProductRequestDTO;
import com.ecommerceapp.products.adapter.transport.rest.mapper.ProductMapper;
import com.ecommerceapp.products.core.port.inbound.results.CreateProductResult;
import com.ecommerceapp.products.core.usecase.ProductUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductUseCase productUseCase;
    private final ProductMapper mapper;

    @PostMapping("/add")
    public ResponseEntity<AppResponse> addProduct(@RequestBody @Valid CreateProductRequestDTO dto) {
        CreateProductResult res = this.productUseCase.createProduct(this.mapper.toCreateProductCommand(dto));
        return AppResponse.initResponse(
                HttpStatus.CREATED,
                true, "create product success",
                this.mapper.toCreateProductResponseDTO(res));
    }

}
