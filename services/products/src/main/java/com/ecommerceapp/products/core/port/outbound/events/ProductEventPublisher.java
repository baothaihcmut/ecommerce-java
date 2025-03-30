package com.ecommerceapp.products.core.port.outbound.events;

import com.ecommerceapp.libs.events.products.ProductAdditionEvent;

public interface ProductEventPublisher {
    void publishAddProductEvent(ProductAdditionEvent event);
}
