package com.yahya.growth.stockmanagementsystem.model;

import javax.annotation.Nullable;

public enum CreditType {

    LENT ("Lent"),
    BORROWED ("Borrowed");

    private final String name;

    CreditType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static CreditType getType(String name) {
//        for (CreditType type: CreditType.values()) {
//            if (type.getName().equalsIgnoreCase(name)) {
//                return type;
//            }
//        }
        if (name.equalsIgnoreCase("payable")) {
            return BORROWED;
        } else if (name.equalsIgnoreCase(("receivable"))) {
            return LENT;
        }
        throw new IllegalArgumentException(String.format("Name (%s) is not a valid credit type", name));
    }

}
