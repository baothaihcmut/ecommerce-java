package com.ecommerceapp.products.core.usecase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerceapp.products.core.domain.entities.ProductItem;
import com.ecommerceapp.products.core.port.inbound.commands.DecreaseProductItemStockCommand;
import com.ecommerceapp.products.core.port.inbound.commands.IncreaseProductItemStockCommand;
import com.ecommerceapp.products.core.port.inbound.handlers.ProductItemStockHandler;
import com.ecommerceapp.products.core.port.inbound.results.DecreaseProductItemStockResult;
import com.ecommerceapp.products.core.port.inbound.results.IncreaseProductItemStockResult;
import com.ecommerceapp.products.core.port.inbound.results.ProductItemResult;
import com.ecommerceapp.products.core.port.outbound.repositories.ProductItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductItemStockUseCase implements ProductItemStockHandler {
    private final ProductItemRepository productItemRepository;

    @Override
    @Transactional
    public IncreaseProductItemStockResult increaseProductItemStock(IncreaseProductItemStockCommand command) {
        Map<String, Integer> mapQuantity = new HashMap<>();
        command.getProductItemStocks().forEach((item) -> {
            mapQuantity.put(item.getProductId(), item.getQuantity());
        });
        List<ProductItem> productItems = productItemRepository.findProductItemByIdList(
                command.getProductItemStocks().stream().map(info -> new ObjectId(info.getProductId())).toList());
        for (ProductItem productItem : productItems) {
            productItem.increaseStock(mapQuantity.get(productItem.getId().toHexString()));
        }
        this.productItemRepository.bulkSave(productItems);
        return IncreaseProductItemStockResult.builder().productItem(
                productItems.stream().map(item -> ProductItemResult.toProductItemResult(item)).toList()).build();
    }

    @Override
    public DecreaseProductItemStockResult decreaseProductItemStock(DecreaseProductItemStockCommand command) {
        Map<String, Integer> mapQuantity = new HashMap<>();
        command.getProductItemStocks().forEach((item) -> {
            mapQuantity.put(item.getProductId(), item.getQuantity());
        });
        List<ProductItem> productItems = productItemRepository.findProductItemByIdList(
                command.getProductItemStocks().stream().map(info -> new ObjectId(info.getProductId())).toList());
        for (ProductItem productItem : productItems) {
            productItem.decreaseStock(mapQuantity.get(productItem.getId().toHexString()));
        }
        this.productItemRepository.bulkSave(productItems);
        return DecreaseProductItemStockResult.builder().productItem(
                productItems.stream().map(item -> ProductItemResult.toProductItemResult(item)).toList()).build();
    }

}
