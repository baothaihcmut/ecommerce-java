package com.ecommerceapp.users.adapter.db.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.ecommerceapp.users.core.domain.entities.Address;
import com.ecommerceapp.users.core.port.outbound.repositories.AddressRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Repository

public class PostgresAddressRepository implements AddressRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Address address) {
        entityManager.merge(address);
    }

    @Override
    public Optional<Address> findAddressById(UUID id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Address> cq = cb.createQuery(Address.class);
        Root<Address> root = cq.from(Address.class);
        cq.where(cb.equal(root.get("id"), id));
        return entityManager.createQuery(cq).getResultList().stream().findFirst();
    }

}
