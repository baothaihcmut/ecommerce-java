package com.ecommerceapp.orders.adapter.db;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ecommerceapp.orders.core.domain.entities.Order;
import com.ecommerceapp.orders.core.domain.enums.OrderStatus;
import com.ecommerceapp.orders.core.port.outbound.repositories.OrderRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

@Repository
public class PostgresOrderRepository implements OrderRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Order order) {
        entityManager.merge(order);
    }

    @Override
    public List<Order> findOrderWithOrderLinesByCreatedAtBeforeTime(Instant time) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> q = cb.createQuery(Order.class);
        Root<Order> root = q.from(Order.class);
        root.join("orderLines", JoinType.INNER);
        q.select(root)
                .where(cb.lessThan(root.get("createdAt"), time));
        return entityManager.createQuery(q).getResultList();
    }

    @Override
    public void bulkCancelOrder(List<Order> orders) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Order> update = cb.createCriteriaUpdate(Order.class);
        Root<Order> root = update.from(Order.class);
        update.set("status", OrderStatus.CANCELLED)
                .set("canceledAt", Instant.now())
                .where(root.get("id").in(
                        orders.stream().map(Order::getId).toList()));
        entityManager.createQuery(update).executeUpdate();

    }

}
