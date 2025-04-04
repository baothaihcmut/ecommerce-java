package com.ecommerceapp.users.adapter.db.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.ecommerceapp.users.core.domain.entities.User;
import com.ecommerceapp.users.core.port.outbound.repositories.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Repository
public class PostgresUserRepository implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(User user) {
        entityManager.merge(user);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> q = cb.createQuery(User.class);
        Root<User> root = q.from(User.class);
        q.select(root).where(cb.equal(root.get("email"), email));
        return entityManager.createQuery(q).getResultList().stream().findFirst();
    }

    @Override
    public Optional<User> findUserByPhoneNumber(String phoneNumber) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> q = cb.createQuery(User.class);
        Root<User> root = q.from(User.class);
        q.select(root).where(cb.equal(root.get("phoneNumber"), phoneNumber));
        return entityManager.createQuery(q).getResultList().stream().findFirst();
    }

    @Override
    public Optional<User> findUserById(UUID id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> q = cb.createQuery(User.class);
        Root<User> root = q.from(User.class);
        q.select(root).where(cb.equal(root.get("id"), id));
        return entityManager.createQuery(q).getResultList().stream().findFirst();
    }

    @Override
    public List<User> findUserByIdList(List<UUID> ids) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> q = cb.createQuery(User.class);
        Root<User> root = q.from(User.class);
        q.select(root).where(root.get("id").in(ids));
        return entityManager.createQuery(q).getResultList();
    }

}
