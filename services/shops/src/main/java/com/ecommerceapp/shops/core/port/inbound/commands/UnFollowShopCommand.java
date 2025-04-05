package com.ecommerceapp.shops.core.port.inbound.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UnFollowShopCommand {
    private String shopId;
}
