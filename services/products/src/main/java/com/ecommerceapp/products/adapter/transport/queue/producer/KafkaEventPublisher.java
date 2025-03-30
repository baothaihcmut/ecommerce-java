package com.ecommerceapp.products.adapter.transport.queue.producer;

import org.springframework.stereotype.Service;

import com.ecommerceapp.libs.events.products.ProductAdditionEvent;
import com.ecommerceapp.libs.kafka.KafkaService;
import com.ecommerceapp.products.core.port.outbound.events.ProductEventPublisher;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaEventPublisher implements ProductEventPublisher {
    private final KafkaService kafkaService;
    private static final String PRODUCT_ADD_TOPIC = "products.addProduct";

    @Override
    public void publishAddProductEvent(ProductAdditionEvent event) {
        this.kafkaService.sendMessage(PRODUCT_ADD_TOPIC, null, event);
    }

}
