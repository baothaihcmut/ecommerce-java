package com.ecommerceapp.shops.adapter.transport.rest.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerceapp.libs.response.AppResponse;
import com.ecommerceapp.shops.adapter.transport.rest.dtos.request.AddShopCategoryRequestDTO;
import com.ecommerceapp.shops.adapter.transport.rest.mappers.ShopCategoryMapper;
import com.ecommerceapp.shops.core.port.inbound.handlers.ShopCategoryHandler;
import com.ecommerceapp.shops.core.port.inbound.results.CreateShopCategoryResult;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/shops/{id}/categories")
@RequiredArgsConstructor
public class ShopCategoryController {
    private final ShopCategoryHandler shopCategoryHandler;
    private final ShopCategoryMapper shopCategoryMapper;

    @PostMapping("/add")
    public ResponseEntity<AppResponse> addShopCategory(
            @PathVariable String id,
            @RequestBody @Valid AddShopCategoryRequestDTO dto) {
        CreateShopCategoryResult res = shopCategoryHandler
                .createShopCategory(shopCategoryMapper.toCreateShopCategoryCommand(dto, id, null));
        return AppResponse.initResponse(
                HttpStatus.CREATED,
                true, "add shop category success",
                shopCategoryMapper.toAddShopCategoryResponseDTO(res));
    }

    @PostMapping("/{categoryId}/sub-categories/add")
    public ResponseEntity<AppResponse> addSubCategory(
            @PathVariable String id,
            @PathVariable String categoryId,
            @RequestBody @Valid AddShopCategoryRequestDTO dto) {
        CreateShopCategoryResult res = shopCategoryHandler.createShopCategory(
                shopCategoryMapper.toCreateShopCategoryCommand(dto, id, categoryId));
        return AppResponse.initResponse(
                HttpStatus.CREATED,
                true,
                "add sub category success",
                shopCategoryMapper.toAddShopCategoryResponseDTO(res));

    }

}
