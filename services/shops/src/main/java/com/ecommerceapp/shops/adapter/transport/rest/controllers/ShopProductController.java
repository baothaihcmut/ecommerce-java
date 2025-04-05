package com.ecommerceapp.shops.adapter.transport.rest.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerceapp.libs.response.AppResponse;
import com.ecommerceapp.shops.adapter.transport.rest.dtos.request.AddShopProductRequestDTO;
import com.ecommerceapp.shops.adapter.transport.rest.mappers.ShopProductMapper;
import com.ecommerceapp.shops.core.port.inbound.handlers.ShopProductHandler;
import com.ecommerceapp.shops.core.port.inbound.queries.GetProductsOfShopQuery;
import com.ecommerceapp.shops.core.port.inbound.results.CreateProductResult;
import com.ecommerceapp.shops.core.port.inbound.results.GetProductsOfShopResult;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/shops/{id}/products")
@RequiredArgsConstructor
public class ShopProductController {
        private final ShopProductHandler shopHandler;
        private final ShopProductMapper shopProductMapper;

        @PostMapping("/add")
        public ResponseEntity<AppResponse> addShopProduct(@PathVariable("id") String id,
                        @RequestBody @Valid AddShopProductRequestDTO dto) {
                CreateProductResult result = shopHandler
                                .createProduct(shopProductMapper.toCreateProductCommand(dto, id));
                return AppResponse.initResponse(
                                HttpStatus.CREATED,
                                true,
                                "create shop product success",
                                shopProductMapper.toCreateProductResponseDTO(result));
        }

        @GetMapping("")
        public ResponseEntity<AppResponse> getProductsOfShop(
                        @PathVariable("id") String shopId,
                        @RequestParam(value = "has_thumbnail", required = false, defaultValue = "false") boolean hasThumbnail,
                        @RequestParam(required = false, defaultValue = "10") int limit,
                        @RequestParam(required = false, defaultValue = "0") int offset) {
                GetProductsOfShopResult res = shopHandler.getProductsOfShop(
                                GetProductsOfShopQuery.builder()
                                                .shopId(shopId)
                                                .limit(limit)
                                                .offset(offset)
                                                .hasThumbnail(hasThumbnail)
                                                .build());
                return AppResponse.initResponse(HttpStatus.OK,
                                true,
                                "get products of shop success",
                                shopProductMapper.toGetProductsOfShopResponseDTO(res));
        }

}
