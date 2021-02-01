package com.yahya.growth.stockmanagementsystem.service.implematation;

import com.yahya.growth.stockmanagementsystem.dao.UserDao;
import com.yahya.growth.stockmanagementsystem.dao.security.RoleDao;
import com.yahya.growth.stockmanagementsystem.model.User;
import com.yahya.growth.stockmanagementsystem.model.security.Role;
import com.yahya.growth.stockmanagementsystem.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;
    private final UserDao userDao; // TODO:HACK This might be a hack of sorts. Doesn't sit well with the overall design of the code so far.

    @Autowired
    public RoleServiceImpl(RoleDao roleDao, UserDao userDao) {
        this.roleDao = roleDao;
        this.userDao = userDao;
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
    public boolean deleteById(Integer id) {
        Optional<User> user = userDao.findOneByRole(new Role(id));
        System.out.println(user);
        if (user.isPresent()) {
            return false;
        }
        roleDao.deleteById(id);
        return true;
    }

    @Override
    public Role findByName(String name) {
        return roleDao.findByName(name).orElseThrow();
    }
}
