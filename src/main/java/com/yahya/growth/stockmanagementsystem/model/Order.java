package com.yahya.growth.stockmanagementsystem.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "Orders") // Since Order is a reserved key word in mysql
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int quantity;
    private String deliveryAddress;
    @ManyToOne(fetch = FetchType.EAGER)
    private Item item;



}
