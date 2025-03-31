package com.ecommerceapp.shops.core.port.inbound.commands;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowShopCommand {
    private ObjectId shopId;
}
