package com.ecommerceapp.shipment.adapter.transport.rest.clients.dtos;

public record GHTKGetShipmentFeeResponseDTO(
        FeeDTO fee) {
    public record FeeDTO(
            String name,
            Integer fee,
            Boolean delivery) {
    }
}
