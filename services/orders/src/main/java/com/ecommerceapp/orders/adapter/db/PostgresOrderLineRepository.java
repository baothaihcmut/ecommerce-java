package com.ecommerceapp.orders.adapter.db;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.stereotype.Repository;

import com.ecommerceapp.orders.core.domain.entities.Order;
import com.ecommerceapp.orders.core.domain.entities.OrderLine;
import com.ecommerceapp.orders.core.domain.enums.OrderStatus;
import com.ecommerceapp.orders.core.port.outbound.repositories.OrderLineRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class PostgresOrderLineRepository implements OrderLineRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public OrderLineAndCountResult findOrderLineOfShopAndCount(String shopId, OrderStatus status, Integer offset,
            Integer limit) {
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<List<OrderLine>> orderLinesFuture = executorService.submit(() -> {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<OrderLine> cq = cb.createQuery(OrderLine.class);
                Root<OrderLine> root = cq.from(OrderLine.class);
                root.fetch("order", JoinType.LEFT);
                Join<OrderLine, Order> orderJoin = root.join("order", JoinType.LEFT);
                cq.where(cb.and(
                        cb.equal(orderJoin.get("shopId"), shopId),
                        cb.equal(orderJoin.get("status"), status)));
                return entityManager.createQuery(cq).setFirstResult(offset).setMaxResults(limit).getResultList();

            });
            Future<Long> countFuture = executorService.submit(() -> {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<Long> cq = cb.createQuery(Long.class);
                Root<OrderLine> root = cq.from(OrderLine.class);
                root.fetch("order", JoinType.LEFT);
                Join<OrderLine, Order> orderJoin = root.join("order", JoinType.LEFT);
                cq.where(cb.and(
                        cb.equal(orderJoin.get("shopId"), shopId),
                        cb.equal(orderJoin.get("status"), status)));
                cq.select(cb.count(root));
                return entityManager.createQuery(cq).getSingleResult();
            });
            return new OrderLineAndCountResult(
                    orderLinesFuture.get(),
                    countFuture.get());

        } catch (Exception exception) {
            log.error(exception.getMessage(), exception.getCause());
            throw new RuntimeException(exception.getMessage(), exception.getCause());
        }
    }

}
