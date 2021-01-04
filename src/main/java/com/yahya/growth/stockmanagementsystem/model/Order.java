package com.yahya.growth.stockmanagementsystem.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "Orders") // Since Order is a reserved key word in mysql
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int quantity;
    private Timestamp orderedTime;
    private Timestamp lastModifiedTime;
    private boolean isPaid;
    @ManyToOne(fetch = FetchType.EAGER)
    private Item item = new Item();
    @ManyToOne(fetch = FetchType.EAGER)
    private Customer customer;

    public Order() {}

}
