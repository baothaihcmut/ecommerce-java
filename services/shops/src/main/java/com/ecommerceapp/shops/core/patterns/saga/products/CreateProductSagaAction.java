package com.ecommerceapp.shops.core.patterns.saga.products;

import com.ecommerceapp.libs.saga.SagaAction;
import com.ecommerceapp.shops.core.domain.entities.Product;
import com.ecommerceapp.shops.core.port.inbound.commands.CreateProductCommand;
import com.ecommerceapp.shops.core.port.outbound.clients.ProductClient;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateProductSagaAction implements SagaAction<Product> {
    private final CreateProductCommand command;
    private final ProductClient productClient;
    private Product product;

    @Override
    public Product execute() {
        Product product = productClient.createProduct(command);
        this.product = product;
        return product;
    }

    @Override
    public void abort() {
        productClient.deleteProduct(product);
    }

}
