package com.yahya.growth.stockmanagementsystem.security.auth;

import java.util.Optional;

public interface ApplicationServiceDao {

    Optional<ApplicationUser> selectApplicationUserByUsername(String username);
}
