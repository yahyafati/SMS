package com.yahya.growth.stockmanagementsystem.service;

import com.yahya.growth.stockmanagementsystem.model.security.Authority;

public interface AuthorityService extends BasicServiceSkeleton<Authority> {

    Authority findByName(String authorityName);
}
