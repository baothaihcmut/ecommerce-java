package com.ecommerceapp.orders.core.domain.entities;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.ecommerceapp.libs.exception.AppException;
import com.ecommerceapp.orders.core.domain.enums.OrderStatus;
import com.ecommerceapp.orders.core.domain.enums.PaymentMethod;
import com.ecommerceapp.orders.core.domain.enums.PaymentProvider;
import com.ecommerceapp.orders.core.domain.enums.ShipProvider;
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = false)
    private String shopId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private PaymentProvider paymentProvider;

    @Column(nullable = true)
    private String recieveAddress;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = true)
    private Instant paidAt;

    @Column(nullable = true)
    private Instant confirmedAt;

    @Column(nullable = true)
    private Instant shippedAt;

    @Column(nullable = true)
    private Instant recievedAt;

    @Column(nullable = true)
    private Instant canceledAt;

    @Column(nullable = false)
    private int totalAmount;

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private ShipProvider shipProvider;

    @Column(nullable = true)
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
        // check all product item in same shop
        if (createOrderLineArgs.size() == 0) {
            throw new AppException(ErrorCode.ORDER_LINE_EQUAL_ZERO);
        }
        if (!checkOrderInSameShop(createOrderLineArgs)) {
            throw new AppException(ErrorCode.ORDER_LINE_IS_IN_DIFFERENT_SHOP);
        }
        this.shopId = createOrderLineArgs.get(0).productItem.getShopId();
        this.checkProductItemOutofStock(createOrderLineArgs);
        this.orderLines = createOrderLineArgs.stream()
                .map((arg) -> new OrderLine(this, arg.productItem(), arg.quantity())).toList();
        this.totalAmount = orderLines.stream().map(OrderLine::getSubTotal).reduce(0, Integer::sum);

    }

    private boolean checkOrderInSameShop(List<CreateOrderLineArg> args) {
        return !args.stream()
                .anyMatch(arg -> !arg.productItem.getShopId().equals(args.get(0).productItem().getShopId()));
    }

    private void checkProductItemOutofStock(List<CreateOrderLineArg> args) {
        // get product out of stock
        List<ProductItem> outOfStockItem = args.stream()
                .filter(item -> item.productItem().getQuantity() < item.quantity())
                .map(item -> item.productItem())
                .toList();
        if (outOfStockItem.size() > 0) {
            throw new AppException(ErrorCode.PRODUCT_ITEM_OUT_OF_STOCK.withDetails(
                    Map.of("productItems", outOfStockItem)));
        }
    }

    public void processPayment(PaymentMethod paymentMethod, PaymentProvider paymentProvider) {
        if (!this.status.equals(OrderStatus.PENDING)) {
            throw new AppException(ErrorCode.ORDER_IS_NOT_PENDING);
        }
        this.status = OrderStatus.PROCESSPAYMENT;
        this.paymentMethod = paymentMethod;
        if (!paymentMethod.equals(PaymentMethod.COD)) {
            if (paymentProvider == null) {
                throw new AppException(ErrorCode.PAYMENT_PROVIDER_IS_REQUIRED);
            }
            this.paymentProvider = paymentProvider;
        } else {
            // if payment method is COD
            this.status = OrderStatus.PAID;
            this.paidAt = Instant.now();
        }
    }

    public void updateRecieveAddress(Address address, Integer shippingFee, ShipProvider shipProvider) {
        this.recieveAddress = address.toString();
        this.shippingCost = shippingFee;
        this.shipProvider = shipProvider;
    }

    public void paymentSuccess() {
        if (!this.status.equals(OrderStatus.PROCESSPAYMENT)) {
            throw new AppException(ErrorCode.ORDER_IS_NOT_PROCESS_PAYMENT);
        }
        this.status = OrderStatus.PAID;
        this.paidAt = Instant.now();

    }

    public Integer getTotalAmountWithShippingFee() {
        return this.totalAmount + this.shippingCost;
    }

    public void confirmOrder() {
        if (!this.status.equals(OrderStatus.PAID)) {
            throw new AppException(ErrorCode.ORDER_IS_NOT_PAID);
        }
        this.confirmedAt = Instant.now();
        this.status = OrderStatus.CONFIRMED;

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
