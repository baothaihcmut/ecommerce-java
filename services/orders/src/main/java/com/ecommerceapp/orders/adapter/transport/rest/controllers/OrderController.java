package com.ecommerceapp.orders.adapter.transport.rest.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerceapp.libs.response.AppResponse;
import com.ecommerceapp.orders.adapter.transport.rest.dtos.request.CreateOrderRequestDTO;
import com.ecommerceapp.orders.adapter.transport.rest.dtos.response.CreateOrderResponseDTO;
import com.ecommerceapp.orders.adapter.transport.rest.mappers.OrderMapper;
import com.ecommerceapp.orders.core.port.inbound.handlers.OrderHandler;
import com.ecommerceapp.orders.core.port.inbound.results.CreateOrderResult;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderHandler orderHandler;
    private final OrderMapper orderMapper;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public AppResponse<CreateOrderResponseDTO> addOrder(@Valid CreateOrderRequestDTO dto) {
        CreateOrderResult result = orderHandler.createOrder(
                orderMapper.toCreateOrderCommand(dto));
        return AppResponse.initResponse(
                true,
                "create order success",
                orderMapper.toCreateOrderResponseDTO(result));
    }

}
