package com.ecommerceapp.users.core.port.inbound.commands;

public record AddUserAddressCommand(
        String address,
        String street,
        String ward,
        String district,
        String province) {

}
