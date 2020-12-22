package com.yahya.growth.stockmanagementsystem.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

@Entity
@Data
public class Item {

    @GeneratedValue
    private int id;
}
