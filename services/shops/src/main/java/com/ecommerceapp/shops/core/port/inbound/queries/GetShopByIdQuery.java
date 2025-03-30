package com.ecommerceapp.shops.core.port.inbound.queries;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetShopByIdQuery {
    private ObjectId shopId;
}
