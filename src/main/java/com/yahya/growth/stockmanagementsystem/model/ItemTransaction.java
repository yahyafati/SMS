package com.yahya.growth.stockmanagementsystem.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ItemTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Item item;
    @ManyToOne
    private Transaction transaction;
    private double unitPrice;
    private int quantity;

}
