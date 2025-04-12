package com.ecommerceapp.users.core.domain.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.mindrot.jbcrypt.BCrypt;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Address> addresses = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Customer customer;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private ShopOwner shopOwner;

    public User(
            UUID id,
            String email,
            String firstName,
            String lastName,
            String phoneNumber,
            Boolean isShopOwnerActive,
            String password) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.isShopOwnerActive = isShopOwnerActive;
        this.password = password;
    }

    public User(
            String email,
            String password,
            String firstName,
            String lastName,
            String phoneNumber) {
        this.id = UUID.randomUUID();
        this.email = email;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt(10));
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isShopOwnerActive = false;
        this.customer = Customer.builder()
                .loyaltyPoint(0)
                .user(this)
                .build();
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class AddAddressArg {
        private String street;
        private String town;
        private String city;
        private String province;
    }

    public void addAddress(AddAddressArg arg) {
        this.addresses.add(
                Address.builder()
                        .user(this)
                        .street(arg.getStreet())
                        .town(arg.getTown())
                        .city(arg.getCity())
                        .province(arg.getProvince())
                        .build());
    }

    public boolean validatePassword(String password) {
        return BCrypt.checkpw(password, this.password);
    }

    public void setCustomer(Customer customer) {
        if (customer != null) {
            customer.setUser(this);
            this.customer = customer;
        }
    }

    public void setShopOwner(ShopOwner shopOwner) {
        if (shopOwner != null) {
            shopOwner.setUser(this);
            this.shopOwner = shopOwner;
        }
    }

    public void activateShopOwner(String bussinessLince) {
        ShopOwner shopOwner = new ShopOwner();
        shopOwner.setBussinessLicense(bussinessLince);
        shopOwner.setUser(this);
        this.shopOwner = shopOwner;
    }

}
