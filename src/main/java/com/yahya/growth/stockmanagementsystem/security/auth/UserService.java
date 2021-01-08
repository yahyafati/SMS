package com.yahya.growth.stockmanagementsystem.security.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//@Service
@Deprecated
public class UserService implements UserDetailsService {

    private final UserServiceDao userServiceDao;

//    @Autowired
    public UserService(@Qualifier("fake") UserServiceDao userServiceDao) {
        this.userServiceDao = userServiceDao;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userServiceDao.selectApplicationUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found.", username)));
    }
}
