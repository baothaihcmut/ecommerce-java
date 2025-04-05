package com.ecommerceapp.shops.core.port.inbound.results;

import java.util.List;

import com.ecommerceapp.libs.response.PaginationResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetFollowersOfShopResult {
    private List<UserResult> followers;
    private PaginationResponse pagination;
}
