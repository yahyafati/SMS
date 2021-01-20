package com.yahya.growth.stockmanagementsystem.model;

public enum CreditType {

    PAID ("Paid"),
    RECEIVED ("Received");

    private final String name;

    CreditType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
