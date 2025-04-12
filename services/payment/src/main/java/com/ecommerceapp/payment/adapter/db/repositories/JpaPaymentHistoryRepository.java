package com.ecommerceapp.payment.adapter.db.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.ecommerceapp.payment.core.domain.entities.PaymentHistory;
import com.ecommerceapp.payment.core.port.outbound.repositories.PaymentHistoryRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Repository
public class JpaPaymentHistoryRepository implements PaymentHistoryRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(PaymentHistory paymentHistory) {
        entityManager.merge(paymentHistory);
    }

    @Override
    public Optional<PaymentHistory> findPaymentHistoryById(UUID id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<PaymentHistory> cq = cb.createQuery(PaymentHistory.class);
        Root<PaymentHistory> root = cq.from(PaymentHistory.class);
        cq.where(cb.equal(root.get("id"), id));
        return entityManager.createQuery(cq).getResultList().stream().findFirst();
    }

}
