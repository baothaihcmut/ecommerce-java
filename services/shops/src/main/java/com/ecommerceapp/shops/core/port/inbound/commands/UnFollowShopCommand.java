package com.ecommerceapp.shops.core.port.inbound.commands;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UnFollowShopCommand {
    private ObjectId shopId;
}
