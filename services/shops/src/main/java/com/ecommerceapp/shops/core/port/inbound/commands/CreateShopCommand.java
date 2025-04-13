package com.ecommerceapp.shops.core.port.inbound.commands;

public record CreateShopCommand(
        String name,
        String description,
        String shopAddress,
        String shopStreet,
        String shopWard,
        String shopDistrict,
        String shopProvince) {
}
