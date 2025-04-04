package com.ecommerceapp.shops.core.port.inbound.queries;

import com.ecommerceapp.libs.queries.PaginationQuery;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetFollowersOfShopQuery {
    private String shopId;
    private PaginationQuery pagination;
}
