package com.ecommerceapp.shops.adapter.transport.rest.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerceapp.libs.response.AppResponse;
import com.ecommerceapp.libs.response.ErrorResponse;
import com.ecommerceapp.shops.adapter.transport.rest.dtos.request.AddShopCategoryRequestDTO;
import com.ecommerceapp.shops.adapter.transport.rest.dtos.response.AddShopCategoryResponseDTO;
import com.ecommerceapp.shops.adapter.transport.rest.mappers.ShopCategoryMapper;
import com.ecommerceapp.shops.core.port.inbound.handlers.ShopCategoryHandler;
import com.ecommerceapp.shops.core.port.inbound.results.CreateShopCategoryResult;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/shops/{id}/categories")
@RequiredArgsConstructor
@Tag(name = "Shop Category", description = "Shop category api")
public class ShopCategoryController {
    private final ShopCategoryHandler shopCategoryHandler;
    private final ShopCategoryMapper shopCategoryMapper;

    @PostMapping("/add")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "create shop category success"),
            @ApiResponse(responseCode = "404", description = "shop not exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "user is not shop owner", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @ResponseStatus(HttpStatus.CREATED)
    public AppResponse<AddShopCategoryResponseDTO> addShopCategory(
            @PathVariable String id,
            @RequestBody @Valid AddShopCategoryRequestDTO dto) {
        CreateShopCategoryResult res = shopCategoryHandler
                .createShopCategory(shopCategoryMapper.toCreateShopCategoryCommand(dto, id, null));
        return AppResponse.initResponse(
                true,
                "add shop category success",
                shopCategoryMapper.toAddShopCategoryResponseDTO(res));
    }

    @PostMapping("/{categoryId}/sub-categories/add")
    @ResponseStatus(HttpStatus.CREATED)
    public AppResponse<AddShopCategoryResponseDTO> addSubCategory(
            @PathVariable String id,
            @PathVariable String categoryId,
            @RequestBody @Valid AddShopCategoryRequestDTO dto) {
        CreateShopCategoryResult res = shopCategoryHandler.createShopCategory(
                shopCategoryMapper.toCreateShopCategoryCommand(dto, id, categoryId));
        return AppResponse.initResponse(
                true,
                "add sub category success",
                shopCategoryMapper.toAddShopCategoryResponseDTO(res));

    }

}
