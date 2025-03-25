package com.ecommerceapp.users.core.domain.entities;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Table(name = "addresses")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    @Id
    private UUID id;

    @Column(name = "priority", nullable = false)
    private int priority;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String town;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String province;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    @ToString.Exclude
    private User user;
}
