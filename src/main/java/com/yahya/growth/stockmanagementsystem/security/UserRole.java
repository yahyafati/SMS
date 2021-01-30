package com.yahya.growth.stockmanagementsystem.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.yahya.growth.stockmanagementsystem.security.Permission.*;

public enum UserRole {
    NEW_STAFF(Sets.newHashSet(
            ITEM_READ, ORDER_READ, CUSTOMER_READ, BRAND_READ, SUBCATEGORY_READ, STORE_READ, CATEGORY_READ,
            ITEM_WRITE, ORDER_WRITE, CUSTOMER_WRITE, TRANSACTION_WRITE, TRANSACTION_READ, COMPANY_READ
    )),
    STAFF(Sets.newHashSet(
            ITEM_READ, ORDER_READ, CUSTOMER_READ, BRAND_READ, SUBCATEGORY_READ, STORE_READ, CATEGORY_READ,
            ITEM_WRITE, ORDER_WRITE, CUSTOMER_WRITE, BRAND_WRITE, SUBCATEGORY_WRITE, STORE_WRITE, CATEGORY_WRITE,
            TRANSACTION_WRITE, TRANSACTION_READ, COMPANY_READ, CREDIT_SETTLEMENT_WRITE, CREDIT_SETTLEMENT_READ
    )),
    MANAGER(Sets.newHashSet(
            ITEM_READ, ORDER_READ, CUSTOMER_READ, BRAND_READ, SUBCATEGORY_READ, STORE_READ, CATEGORY_READ,
            ITEM_WRITE, ORDER_WRITE, CUSTOMER_WRITE, BRAND_WRITE, SUBCATEGORY_WRITE, STORE_WRITE, CATEGORY_WRITE,
            USER_READ, USER_WRITE,
            ROLE_READ, ROLE_WRITE, TRANSACTION_READ, TRANSACTION_WRITE, CREDIT_SETTLEMENT_READ, CREDIT_SETTLEMENT_WRITE,
            COMPANY_READ, COMPANY_WRITE,
            VIEW_STORE_REPORT
    )),
    OWNER(Sets.newHashSet(
            ITEM_READ, ORDER_READ, CUSTOMER_READ, BRAND_READ, SUBCATEGORY_READ, STORE_READ, CATEGORY_READ,
            ITEM_WRITE, ORDER_WRITE, CUSTOMER_WRITE, BRAND_WRITE, SUBCATEGORY_WRITE, STORE_WRITE, CATEGORY_WRITE,
            USER_READ, USER_WRITE,VIEW_STORE_REPORT, VIEW_ALL_REPORT,
            ROLE_READ, ROLE_WRITE, TRANSACTION_READ, TRANSACTION_WRITE, CREDIT_SETTLEMENT_READ, CREDIT_SETTLEMENT_WRITE,
            COMPANY_READ, COMPANY_WRITE
    )),
    IT_PERSON(Sets.newHashSet(IT_PERMISSION));

    private final Set<Permission> permissions;

    UserRole(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
