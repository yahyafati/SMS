package com.yahya.growth.stockmanagementsystem.service.implematation;

import com.yahya.growth.stockmanagementsystem.dao.security.RoleDao;
import com.yahya.growth.stockmanagementsystem.model.security.Role;
import com.yahya.growth.stockmanagementsystem.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Role findById(int id) {
        return roleDao.findById(id).orElseThrow();
    }

    @Override
    public Role save(Role role) {
        if (!role.getName().toUpperCase().startsWith("ROLE_")) {
            role.setName("ROLE_" + role.getName().toUpperCase());
        }
        return roleDao.save(role);
    }

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        roleDao.deleteById(id);
    }

    @Override
    public Role findByName(String name) {
        return roleDao.findByName(name).orElseThrow();
    }
}
