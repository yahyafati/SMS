package com.yahya.growth.stockmanagementsystem.security;

import com.google.common.collect.Sets;

import java.util.Set;

import static com.yahya.growth.stockmanagementsystem.security.Permission.*;

public enum UserRole {
    NEW_STAFF(Sets.newHashSet(
            ITEM_READ, ORDER_READ, CUSTOMER_READ, BRAND_READ, SUBCATEGORY_READ, STORE_READ, CATEGORY_READ,
            ITEM_WRITE, ORDER_WRITE, CUSTOMER_WRITE
    )),
    STAFF(Sets.newHashSet(
            ITEM_READ, ORDER_READ, CUSTOMER_READ, BRAND_READ, SUBCATEGORY_READ, STORE_READ, CATEGORY_READ,
            ITEM_WRITE, ORDER_WRITE, CUSTOMER_WRITE, BRAND_WRITE, SUBCATEGORY_WRITE, STORE_WRITE, CATEGORY_WRITE
    )),
    MANAGER(Sets.newHashSet(
            ITEM_READ, ORDER_READ, CUSTOMER_READ, BRAND_READ, SUBCATEGORY_READ, STORE_READ, CATEGORY_READ,
            ITEM_WRITE, ORDER_WRITE, CUSTOMER_WRITE, BRAND_WRITE, SUBCATEGORY_WRITE, STORE_WRITE, CATEGORY_WRITE,
            VIEW_STORE_REPORT
    )),
    OWNER(Sets.newHashSet(
            ITEM_READ, ORDER_READ, CUSTOMER_READ, BRAND_READ, SUBCATEGORY_READ, STORE_READ, CATEGORY_READ,
            ITEM_WRITE, ORDER_WRITE, CUSTOMER_WRITE, BRAND_WRITE, SUBCATEGORY_WRITE, STORE_WRITE, CATEGORY_WRITE,
            VIEW_STORE_REPORT, VIEW_ALL_REPORT
    )),
    IT_PERSON(Sets.newHashSet(IT_PERMISSION));

    private final Set<Permission> permissions;

    UserRole(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }
}
