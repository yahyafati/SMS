package com.yahya.growth.stockmanagementsystem.dao;

import com.yahya.growth.stockmanagementsystem.model.User;
import com.yahya.growth.stockmanagementsystem.model.security.Role;
import com.yahya.growth.stockmanagementsystem.model.security.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

    Optional<User> findUserByUsername(String username);

    @Query("select 1 from User u where u.userRole.role=:role")
    Optional<User> findOneByRole(@Param("role")Role role);

    @Query("select u from User u where u.userRole.isDefault =true and u.userRole.role=:role")
    Set<User> findAllByRoleAndIsDefault(@Param("role") Role role);

}
