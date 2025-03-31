package com.ecommerceapp.shops.core.port.inbound.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateShopCommand {
    private String id;
    private String name;
    private String description;
}
