package com.ecommerceapp.shops.adapter.transport.rest.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerceapp.libs.response.AppResponse;
import com.ecommerceapp.shops.adapter.transport.rest.dtos.request.AddShopProductRequestDTO;
import com.ecommerceapp.shops.adapter.transport.rest.dtos.response.AddShopProductResponseDTO;
import com.ecommerceapp.shops.adapter.transport.rest.dtos.response.GetProductsOfShopResponseDTO;
import com.ecommerceapp.shops.adapter.transport.rest.mappers.ShopProductMapper;
import com.ecommerceapp.shops.core.port.inbound.handlers.ShopProductHandler;
import com.ecommerceapp.shops.core.port.inbound.queries.GetProductsOfShopQuery;
import com.ecommerceapp.shops.core.port.inbound.results.CreateProductResult;
import com.ecommerceapp.shops.core.port.inbound.results.GetProductsOfShopResult;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/shops/{id}/products")
@RequiredArgsConstructor
@Tag(name = "Shop Product", description = "shop product api")
public class ShopProductController {
        private final ShopProductHandler shopHandler;
        private final ShopProductMapper shopProductMapper;

        @PostMapping("/add")
        @ResponseStatus(HttpStatus.CREATED)
        public AppResponse<AddShopProductResponseDTO> addShopProduct(@PathVariable("id") String id,
                        @RequestBody @Valid AddShopProductRequestDTO dto) {
                CreateProductResult result = shopHandler
                                .createProduct(shopProductMapper.toCreateProductCommand(dto, id));
                return AppResponse.initResponse(
                                true,
                                "create shop product success",
                                shopProductMapper.toCreateProductResponseDTO(result));
        }

        @GetMapping("")
        @ResponseStatus(HttpStatus.OK)
        public AppResponse<GetProductsOfShopResponseDTO> getProductsOfShop(
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
                return AppResponse.initResponse(
                                true,
                                "get products of shop success",
                                shopProductMapper.toGetProductsOfShopResponseDTO(res));
        }

}
