package com.yahya.growth.stockmanagementsystem.service;

import com.yahya.growth.stockmanagementsystem.model.User;
import com.yahya.growth.stockmanagementsystem.model.security.Role;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

public interface UserService extends UserDetailsService, BasicServiceSkeleton<User> {

    User findByUsername(String username);

    User saveUserWithRoles(User user, Role role);

    boolean checkIfPasswordIsValid(String username, String password);

    boolean checkIfPasswordIsValid(User user, String password);

    @Transactional
    User saveNewUser(User user);

    User changePassword(String username, String newPassword);

    User changePassword(User user, String newPassword);
}
