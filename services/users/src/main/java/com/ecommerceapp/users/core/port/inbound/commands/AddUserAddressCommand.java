package com.ecommerceapp.users.core.port.inbound.commands;

public record AddUserAddressCommand(
        String street,
        String town,
        String city,
        String province) {

}
