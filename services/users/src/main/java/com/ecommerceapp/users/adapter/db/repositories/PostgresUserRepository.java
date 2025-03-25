package com.ecommerceapp.users.adapter.db.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerceapp.users.core.domain.entities.User;
import com.ecommerceapp.users.core.port.outbound.repositories.UserRepository;


@Repository
public interface PostgresUserRepository extends UserRepository, JpaRepository<User,UUID>{
    
    @Query("SELECT u FROM User u WHERE u.email=:email")
    Optional<User> findUserByEmail(@Param("email") String email);
    
    @Query("SELECT u FROM User u WHERE u.phoneNumber=:phoneNumber")
    Optional<User> findUserByPhoneNumber(@Param("phoneNumber") String phoneNumber);
}
