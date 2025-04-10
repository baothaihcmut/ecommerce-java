package com.ecommerceapp.orders.core.domain.entities;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.ecommerceapp.libs.exception.AppException;
import com.ecommerceapp.orders.core.domain.enums.OrderStatus;
import com.ecommerceapp.orders.core.domain.enums.PaymentMethod;
import com.ecommerceapp.orders.core.exception.ErrorCode;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String userId;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private OrderStatus status;

    @Enumerated(EnumType.ORDINAL)
    @Column()
    private PaymentMethod paymentMethod;

    @Column()
    private String recieveAddressId;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    private Instant confirmedAt;
    private Instant shippedAt;
    private Instant recievedAt;
    private Instant canceledAt;

    @Column(nullable = false)
    private int totalAmount;

    @Column()
    private String shippingProviderId;

    @Column()
    private int shippingCost;

    @Column(nullable = false)
    private int quantity;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderLine> orderLines;

    public record CreateOrderLineArg(ProductItem productItem, Integer quantity) {
    }

    public Order(
            String userId,
            List<CreateOrderLineArg> createOrderLineArgs) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.createdAt = Instant.now();
        this.status = OrderStatus.PENDING;
        this.totalAmount = orderLines.stream().map(OrderLine::getSubTotal).reduce(0, Integer::sum);
        // check all product item in same shop
        if (createOrderLineArgs.size() == 0) {
            throw new AppException(ErrorCode.ORDER_LINE_EQUAL_ZERO);
        }
        if (!checkOrderInSameShop(createOrderLineArgs)) {
            throw new AppException(ErrorCode.ORDER_LINE_IS_IN_DIFFERENT_SHOP);
        }
        this.checkProductItemOutofStock(createOrderLineArgs);
        this.orderLines = createOrderLineArgs.stream()
                .map((arg) -> new OrderLine(this, arg.productItem(), arg.quantity())).toList();

    }

    private boolean checkOrderInSameShop(List<CreateOrderLineArg> args) {
        return !args.stream()
                .anyMatch(arg -> !arg.productItem.getShopId().equals(args.get(0).productItem().getShopId()));
    }

    private void checkProductItemOutofStock(List<CreateOrderLineArg> args) {
        // get product out of stock
        List<ProductItem> outOfStockItem = args.stream()
                .filter(item -> item.productItem().getQuantity() > item.quantity())
                .map(item -> item.productItem())
                .toList();
        if (outOfStockItem.size() > 0) {
            throw new AppException(ErrorCode.PRODUCT_ITEM_OUT_OF_STOCK.withDetails(
                    Map.of("productItems", outOfStockItem)));
        }
    }

    public void confirmOrder(
            String userId,
            PaymentMethod paymentMethod,
            String recievedAddressId,
            Integer shippingCost) {
        if (!userId.equals(this.userId)) {
            throw new AppException(ErrorCode.ORDER_IS_NOT_BELONG_TO_USER);
        }
        if (!this.status.equals(OrderStatus.PENDING)) {
            throw new AppException(ErrorCode.ORDER_IS_NOT_PENDING);
        }
        this.paymentMethod = paymentMethod;
        this.shippingCost = shippingCost;
        this.confirmedAt = Instant.now();
        this.recieveAddressId = recievedAddressId;
    }

    public void shippedOrder() {
        if (!this.status.equals(OrderStatus.CONFIRMED)) {
            throw new AppException(ErrorCode.ORDER_IS_NOT_CONFIRMED);
        }
        this.shippedAt = Instant.now();
        this.status = OrderStatus.SHIPPED;
    }

    public void recievedOrder() {
        if (!this.status.equals(OrderStatus.SHIPPED)) {
            throw new AppException(ErrorCode.ORDER_IS_NOT_SHIPPED);
        }
        this.recievedAt = Instant.now();
        this.status = OrderStatus.DELIVERED;
    }

    public void canceledOrder() {
        this.status = OrderStatus.CANCELLED;
        this.canceledAt = Instant.now();
    }

}
