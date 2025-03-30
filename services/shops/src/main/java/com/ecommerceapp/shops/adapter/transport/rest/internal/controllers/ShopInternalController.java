package com.ecommerceapp.shops.adapter.transport.rest.internal.controllers;

import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerceapp.libs.dtos.shop.response.GetShopByIdResponseDTO;
import com.ecommerceapp.shops.adapter.transport.rest.internal.mappers.ShopInternalMapper;
import com.ecommerceapp.shops.core.port.inbound.queries.GetShopByIdQuery;
import com.ecommerceapp.shops.core.port.inbound.results.GetShopByIdResult;
import com.ecommerceapp.shops.core.usecase.ShopUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/internal/shops")
@RequiredArgsConstructor
public class ShopInternalController {
    private final ShopUseCase shopUseCase;
    private final ShopInternalMapper shopMapper;

    @GetMapping("/{id}")
    public ResponseEntity<GetShopByIdResponseDTO> getShopById(@PathVariable("id") ObjectId shopId) {
        GetShopByIdResult res = shopUseCase.getShopById(GetShopByIdQuery.builder().shopId(shopId).build());
        return ResponseEntity.ok().body(shopMapper.toGetShopByIdResponse(res));
    }

}
