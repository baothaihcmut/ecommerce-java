package com.ecommerceapp.payment.core.port.outbound.repositories;

import java.util.Optional;
import java.util.UUID;

import com.ecommerceapp.payment.core.domain.entities.PaymentHistory;

public interface PaymentHistoryRepository {
    void save(PaymentHistory paymentHistory);

    Optional<PaymentHistory> findPaymentHistoryById(UUID id);
}
