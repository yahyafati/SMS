package com.yahya.growth.stockmanagementsystem.service;

import com.yahya.growth.stockmanagementsystem.model.UserProfile;

public interface UserProfileService extends BasicServiceSkeleton<UserProfile>{

    UserProfile findByUsername(String username);
}
