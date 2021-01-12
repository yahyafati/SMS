package com.yahya.growth.stockmanagementsystem.dao.security;

import com.yahya.growth.stockmanagementsystem.model.security.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleDao extends JpaRepository<UserRole, Integer> {
}
