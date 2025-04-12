package com.ecommerceapp.products.adapter.transport.queue.rabbitmq;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.ecommerceapp.libs.events.orders.BulkOrderCanceledEvent;
import com.ecommerceapp.libs.events.orders.OrderCanceledEvent;
import com.ecommerceapp.libs.events.orders.OrderCreatedEvent;
import com.ecommerceapp.products.core.port.inbound.commands.DecreaseProductItemStockCommand;
import com.ecommerceapp.products.core.port.inbound.commands.IncreaseProductItemStockCommand;
import com.ecommerceapp.products.core.port.inbound.commands.DecreaseProductItemStockCommand.ProductItemStockInfo;
import com.ecommerceapp.products.core.port.inbound.handlers.ProductItemStockHandler;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RabbitMqStockHandler {
    private final ProductItemStockHandler productItemStockHandler;

    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "products.stock-decrease", durable = "true"), exchange = @Exchange(value = "orders.events", ignoreDeclarationExceptions = "true"), key = "orders.created"))
    public void handleOrderCreatedEvent(OrderCreatedEvent event) {
        System.out.println("Handle order created event");
        productItemStockHandler.decreaseProductItemStock(new DecreaseProductItemStockCommand(
                event.order().orderLines().stream()
                        .map(item -> new ProductItemStockInfo(item.productItemId(), item.quantity())).toList()));
    }

    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "products.stock-increase", durable = "true"), exchange = @Exchange(value = "orders.events", ignoreDeclarationExceptions = "true"), key = "orders.bulk-canceled"))
    public void handleBulkOrderCanceled(BulkOrderCanceledEvent event) {
        List<ProductItemStockInfo> stockInfoList = event.orders().stream()
                .flatMap(order -> order.orderLines().stream())
                .map(line -> new ProductItemStockInfo(line.productItemId(), line.quantity()))
                .toList();
        productItemStockHandler.increaseProductItemStock(new IncreaseProductItemStockCommand(stockInfoList));
    }

    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "products.stock-increase", durable = "true"), exchange = @Exchange(value = "orders.events", ignoreDeclarationExceptions = "true"), key = "orders.bulk-canceled"))
    public void handleOrderCanceled(OrderCanceledEvent event) {
        List<ProductItemStockInfo> stockInfoList = event.order().orderLines().stream()
                .map(line -> new ProductItemStockInfo(line.productItemId(), line.quantity()))
                .toList();
        productItemStockHandler.increaseProductItemStock(new IncreaseProductItemStockCommand(stockInfoList));
    }

}
