package com.ecommerceapp.orders.adapter.transport.rest.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerceapp.libs.response.AppResponse;
import com.ecommerceapp.orders.adapter.transport.rest.dtos.request.CreateOrderRequestDTO;
import com.ecommerceapp.orders.adapter.transport.rest.dtos.request.MakeOrderPaymentRequestDTO;
import com.ecommerceapp.orders.adapter.transport.rest.dtos.request.UpdateOrderAddressRequestDTO;
import com.ecommerceapp.orders.adapter.transport.rest.dtos.response.CreateOrderResponseDTO;
import com.ecommerceapp.orders.adapter.transport.rest.dtos.response.MakeOrderPaymentResponseDTO;
import com.ecommerceapp.orders.adapter.transport.rest.dtos.response.UpdateOrderAddressResponseDTO;
import com.ecommerceapp.orders.adapter.transport.rest.mappers.OrderMapper;
import com.ecommerceapp.orders.core.port.inbound.commands.UpdateOrderAddressCommand;
import com.ecommerceapp.orders.core.port.inbound.handlers.OrderHandler;
import com.ecommerceapp.orders.core.port.inbound.results.CreateOrderResult;
import com.ecommerceapp.orders.core.port.inbound.results.MakeOrderPaymentResult;
import com.ecommerceapp.orders.core.port.inbound.results.UpdateOrderAddressResult;

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
        public AppResponse<CreateOrderResponseDTO> addOrder(
                        @RequestBody @Valid CreateOrderRequestDTO dto) {
                CreateOrderResult result = orderHandler.createOrder(
                                orderMapper.toCreateOrderCommand(dto));
                return AppResponse.initResponse(
                                true,
                                "create order success",
                                orderMapper.toCreateOrderResponseDTO(result));
        }

        @PatchMapping("/{id}/update-address")
        @ResponseStatus(HttpStatus.CREATED)
        public AppResponse<UpdateOrderAddressResponseDTO> updateOrderAddress(
                        @PathVariable String id,
                        @RequestBody @Valid UpdateOrderAddressRequestDTO requestDTO) {
                UpdateOrderAddressResult result = orderHandler.updateOrderAddress(
                                new UpdateOrderAddressCommand(id, requestDTO.addressId(), requestDTO.shipProvider()));
                return AppResponse.initResponse(
                                true,
                                "update order address success",
                                orderMapper.toUpdateOrderResponseDTO(result));
        }

        @PatchMapping("/{id}/make-payment")
        @ResponseStatus(HttpStatus.CREATED)
        public AppResponse<MakeOrderPaymentResponseDTO> makeOrderPayment(
                        @PathVariable String id,
                        @RequestHeader(value = "X-Forwarded-For", defaultValue = "unknown") String xForwardedFor,
                        @RequestBody @Valid MakeOrderPaymentRequestDTO makeOrderPaymentRequestDTO) {
                MakeOrderPaymentResult result = orderHandler.makeOrderPayment(
                                orderMapper.toMakeOrderPaymentCommand(makeOrderPaymentRequestDTO, id,
                                                xForwardedFor.split(",")[0]));
                return AppResponse.initResponse(
                                true,
                                "make order payment success",
                                orderMapper.toMakeOrderPaymentResponseDTO(result));
        }

}
