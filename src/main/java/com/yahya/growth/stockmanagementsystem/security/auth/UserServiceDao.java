package com.yahya.growth.stockmanagementsystem.security.auth;

import com.yahya.growth.stockmanagementsystem.model.security.User;

import java.util.Optional;

@Deprecated
public interface UserServiceDao {

    Optional<User> selectApplicationUserByUsername(String username);
}
