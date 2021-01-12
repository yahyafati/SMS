package com.yahya.growth.stockmanagementsystem.service.implematation;

import com.yahya.growth.stockmanagementsystem.dao.security.UserRoleDao;
import com.yahya.growth.stockmanagementsystem.model.security.UserRole;
import com.yahya.growth.stockmanagementsystem.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleDao userRoleDao;

    @Autowired
    public UserRoleServiceImpl(UserRoleDao userRoleDao) {
        this.userRoleDao = userRoleDao;
    }

    @Override
    public UserRole findById(int id) {
        return userRoleDao.findById(id).orElseThrow();
    }

    @Override
    public UserRole save(UserRole item) {
        return userRoleDao.save(item);
    }

    @Override
    public List<UserRole> findAll() {
        return userRoleDao.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        userRoleDao.deleteById(id);
    }
}
