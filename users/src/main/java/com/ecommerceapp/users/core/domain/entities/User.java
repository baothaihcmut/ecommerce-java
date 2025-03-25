package com.ecommerceapp.users.core.domain.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User {

    @Id
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    

    @Column(name = "is_shop_owner_active")
    private boolean isShopOwnerActive;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "current_refresh_token")
    private String currentRefreshToken;


    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Address> addresses = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade =  CascadeType.ALL)
    private Customer customer;


    @OneToOne(mappedBy = "user",cascade =  CascadeType.ALL)
    private ShopOwner shopOwner;



    public void addAddress(Address address) {
        this.addresses.add(address);
        address.setUser(this);
    }

    public void setCustomer(Customer customer) {
        customer.setUser(this);
    }
    public void setShopOwner(ShopOwner shopOwner) {
        shopOwner.setUser(this);
    }
}
