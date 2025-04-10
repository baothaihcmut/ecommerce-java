package com.ecommerceapp.products.core.port.inbound.queries;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetProductItemWithProductQuery {
    List<String> ids;
}
