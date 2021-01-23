package com.yahya.growth.stockmanagementsystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private int id;
    @Column(name = "customer_name")
    private String name;
    // TODO Make all addresses atomic
    @Column(name = "customer_address")
    private String address;
    @Column(name = "customer_phone")
    private String phone;
    @Column(name = "customer_email")
    private String email;

    public Customer(int id) {
        this.id = id;
    }
}
