package com.yahya.growth.stockmanagementsystem.security;

public enum Permission {
    ITEM_READ("item:read"),
    ITEM_WRITE("item:write"),
    BRAND_READ("brand_read"),
    BRAND_WRITE("brand_write"),
    CATEGORY_READ("category:read"),
    CATEGORY_WRITE("category:write"),
    SUBCATEGORY_READ("subcategory:read"),
    SUBCATEGORY_WRITE("subcategory:write"),
    STORE_READ("store:read"),
    STORE_WRITE("store:write"),
    CUSTOMER_READ("customer:read"),
    CUSTOMER_WRITE("customer:write"),
    ORDER_READ("order:read"),
    ORDER_WRITE("order:write"),
    VIEW_STORE_REPORT("report:store"),
    VIEW_ALL_REPORT("report:all"),
    IT_PERMISSION("it:reset");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
