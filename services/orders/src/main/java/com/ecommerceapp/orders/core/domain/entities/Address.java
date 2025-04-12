package com.ecommerceapp.orders.core.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String id;
    private String userId;
    private String street;
    private String town;
    private String city;
    private String province;

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s", street, town, city, province);
    }
}
