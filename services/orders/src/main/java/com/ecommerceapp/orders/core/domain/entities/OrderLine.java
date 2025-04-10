package com.ecommerceapp.orders.core.domain.entities;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_lines")
public class OrderLine {
    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "orderId", nullable = false)
    private Order order;

    @Column(nullable = false)
    private String productItemId;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int subTotal;

    public OrderLine(
            Order order,
            ProductItem item,
            Integer quantity) {
        this.id = UUID.randomUUID();
        this.order = order;
        this.quantity = quantity;
        this.productItemId = item.getId();
        this.subTotal = item.getPrice() * quantity;
    }
}
