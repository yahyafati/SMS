package com.yahya.growth.stockmanagementsystem.model.utility;

import lombok.Data;

@Data
public class Company{

    private String name;
    private Branch branch;
    private float vat;
    private float serviceCharge;
    private float specialDiscount;

}
