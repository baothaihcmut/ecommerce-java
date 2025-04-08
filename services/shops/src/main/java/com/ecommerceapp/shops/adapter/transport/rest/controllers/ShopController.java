package com.ecommerceapp.shops.adapter.transport.rest.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerceapp.libs.response.AppResponse;
import com.ecommerceapp.shops.adapter.transport.rest.dtos.request.AddShopRequestDTO;
import com.ecommerceapp.shops.adapter.transport.rest.dtos.response.AddShopResponseDTO;
import com.ecommerceapp.shops.adapter.transport.rest.mappers.ShopMapper;
import com.ecommerceapp.shops.core.port.inbound.handlers.ShopHandler;
import com.ecommerceapp.shops.core.port.inbound.results.CreateShopResult;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/shops")
@RequiredArgsConstructor
@Tag(name = "Shop", description = "shop controller")
public class ShopController {
    private final ShopHandler shopHandler;

    private final ShopMapper shopMapper;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public AppResponse<AddShopResponseDTO> addShop(@RequestBody @Valid AddShopRequestDTO dto) {
        CreateShopResult res = shopHandler.createShop(shopMapper.toCreateShopCommand(dto));
        return AppResponse.initResponse(
                true,
                "create shop success",
                shopMapper.toCreateShopResponseDTO(res));
    }

}
