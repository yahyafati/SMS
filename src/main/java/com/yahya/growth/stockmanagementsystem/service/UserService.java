package com.yahya.growth.stockmanagementsystem.service;

import com.yahya.growth.stockmanagementsystem.model.User;
import com.yahya.growth.stockmanagementsystem.model.security.Role;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

public interface UserService extends UserDetailsService, BasicServiceSkeleton<User> {

    User saveUserWithRoles(User user, Role role);

    @Transactional
    User saveNewUser(User user);
}
