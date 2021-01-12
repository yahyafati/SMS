package com.yahya.growth.stockmanagementsystem.service;

import com.yahya.growth.stockmanagementsystem.model.security.Role;
import com.yahya.growth.stockmanagementsystem.model.security.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService, BasicServiceSkeleton<User> {

    User saveNewUser(User user, Role role);
}
