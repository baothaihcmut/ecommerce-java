package com.ecommerceapp.shops.adapter.transport.queue.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.ecommerceapp.libs.events.products.ProductAdditionEvent;
import com.ecommerceapp.shops.core.port.inbound.handlers.ShopEventHandler;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductEventListener {
    private static final String PRODUCT_ADD_TOPIC = "products.addProduct";
    private final ShopEventHandler shopEventHandler;

    @KafkaListener(topics = PRODUCT_ADD_TOPIC, groupId = "shops-service", properties = {
            "spring.json.value.default.type=com.ecommerceapp.libs.events.products.ProductAdditionEvent" })
    public void handleAddProductEvent(ProductAdditionEvent event) {
        shopEventHandler.handleAddProduct(event);
    }
}
