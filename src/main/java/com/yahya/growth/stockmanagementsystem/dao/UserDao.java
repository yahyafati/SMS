package com.yahya.growth.stockmanagementsystem.dao;

import com.yahya.growth.stockmanagementsystem.model.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

    Optional<User> findUserByUsername(String username);

}
