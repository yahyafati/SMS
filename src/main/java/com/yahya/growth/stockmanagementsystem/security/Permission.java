package com.yahya.growth.stockmanagementsystem.security;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum Permission {
    ITEM_READ("item:read"),
    ITEM_WRITE("item:write"),
    BRAND_READ("brand:read"),
    BRAND_WRITE("brand:write"),
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
    IT_PERMISSION("it:reset"),
    USER_READ("user:read"),
    USER_WRITE("user:write"),
    VIEW_STORE_REPORT("report:store"),
    VIEW_ALL_REPORT("report:all");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

    public static Set<String> getPermissionParents() {
        return Arrays.stream(values())
                .map(Permission::getPermission)
                .map(s -> StringUtils.substringBefore(s, ":"))
                .collect(Collectors.toSet());
    }
}
