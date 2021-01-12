package com.yahya.growth.stockmanagementsystem.model;

public enum TransactionType {

    PURCHASE ("Purchase"),
    SALE ("Sale");

    private final String name;

    TransactionType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
