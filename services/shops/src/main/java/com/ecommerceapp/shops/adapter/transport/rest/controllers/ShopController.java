package com.ecommerceapp.shops.adapter.transport.rest.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerceapp.libs.response.AppResponse;
import com.ecommerceapp.shops.adapter.transport.rest.dtos.request.AddShopRequestDTO;
import com.ecommerceapp.shops.adapter.transport.rest.mappers.ShopMapper;
import com.ecommerceapp.shops.core.port.inbound.handlers.ShopHandler;
import com.ecommerceapp.shops.core.port.inbound.results.CreateShopResult;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/shops")
@RequiredArgsConstructor
public class ShopController {
    private final ShopHandler shopHandler;

    private final ShopMapper shopMapper;

    @PostMapping("/add")
    public ResponseEntity<AppResponse> addShop(@RequestBody @Valid AddShopRequestDTO dto) {
        CreateShopResult res = shopHandler.createShop(shopMapper.toCreateShopCommand(dto));
        return AppResponse.initResponse(
                HttpStatus.CREATED,
                true,
                "create shop success",
                shopMapper.toCreateShopResponseDTO(res));
    }

}
