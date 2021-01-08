package com.yahya.growth.stockmanagementsystem.dao.security;

import com.yahya.growth.stockmanagementsystem.model.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleDao extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(String name);

}
