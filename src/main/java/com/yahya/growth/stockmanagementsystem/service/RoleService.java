package com.yahya.growth.stockmanagementsystem.service;

import com.yahya.growth.stockmanagementsystem.model.security.Role;

public interface RoleService extends BasicServiceSkeleton<Role> {

    Role findByName(String name);
}
