package com.ecommerceapp.users.core.domain.entities;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Table(name = "shop_owners")
@NoArgsConstructor
public class ShopOwner {
    @Id
    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "bussiness_license")
    private String bussinessLicense;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    @JsonBackReference
    private User user;
}
