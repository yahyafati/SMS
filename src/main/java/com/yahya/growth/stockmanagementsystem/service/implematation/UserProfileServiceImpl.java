package com.yahya.growth.stockmanagementsystem.service.implematation;

import com.yahya.growth.stockmanagementsystem.dao.UserProfileDao;
import com.yahya.growth.stockmanagementsystem.model.UserProfile;
import com.yahya.growth.stockmanagementsystem.service.UserProfileService;
import com.yahya.growth.stockmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileDao userProfileDao;
    private final UserService userService;

    @Autowired
    public UserProfileServiceImpl(UserProfileDao userProfileDao, UserService userService) {
        this.userProfileDao = userProfileDao;
        this.userService = userService;
    }

    @Override
    public UserProfile findById(int id) {
        return userProfileDao.findById(id).orElseThrow();
    }

    @Override
    public UserProfile save(UserProfile userProfile) {
        return userProfileDao.save(userProfile);
    }

    @Override
    public List<UserProfile> findAll() {
        return userProfileDao.findAll();
    }

    @Override
    public boolean deleteById(Integer id) {
        userProfileDao.deleteById(id);
        return true;
    }

    @Override
    public UserProfile findByUsername(String username) {
        return userService.findByUsername(username).getProfile();
    }
}
