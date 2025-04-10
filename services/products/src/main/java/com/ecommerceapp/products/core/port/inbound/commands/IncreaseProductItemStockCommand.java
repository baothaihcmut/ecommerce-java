package com.ecommerceapp.products.core.port.inbound.commands;

import java.util.List;

import com.ecommerceapp.products.core.port.inbound.commands.DecreaseProductItemStockCommand.ProductItemStockInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IncreaseProductItemStockCommand {
    List<ProductItemStockInfo> productItemStocks;
}
