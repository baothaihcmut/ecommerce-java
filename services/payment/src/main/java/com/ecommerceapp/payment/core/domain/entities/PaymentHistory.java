package com.ecommerceapp.payment.core.domain.entities;

import java.time.Instant;
import java.util.UUID;

import com.ecommerceapp.payment.core.domain.enums.PaymentProvider;
import com.ecommerceapp.payment.core.domain.enums.PaymentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payment_histories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentHistory {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String orderId;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private Integer amount;

    @Enumerated(EnumType.ORDINAL)
    private PaymentProvider paymentProvider;

    @Enumerated(EnumType.ORDINAL)
    private PaymentStatus paymentStatus;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = true)
    private Instant successAt;

    @Column(nullable = true)
    private Instant failureAt;

    public PaymentHistory(
            String userId,
            String orderId,
            Integer amount,
            PaymentProvider paymentProvider) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.orderId = orderId;
        this.amount = amount;
        this.paymentProvider = paymentProvider;
        this.createdAt = Instant.now();
    }

    public void paymentSuccess() {
        this.paymentStatus = PaymentStatus.SUCCESS;
        this.successAt = Instant.now();
    }

    public void paymentFailure() {
        this.paymentStatus = PaymentStatus.FAILURE;
        this.failureAt = Instant.now();
    }

}
