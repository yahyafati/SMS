package com.yahya.growth.stockmanagementsystem.model;

import javax.annotation.Nullable;

public enum SettlementType {
    PAID ("Paid"),
    RECEIVED ("Received");

    private final String name;

    SettlementType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Nullable
    public  SettlementType getType(String name) {
        for (SettlementType type: SettlementType.values()) {
            if (type.getName().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }

    public CreditType getCreditType() {
        if (this == PAID) {
            return CreditType.BORROWED;
        } else {
            return CreditType.LENT;
        }
    }
}
