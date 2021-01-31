package com.yahya.growth.stockmanagementsystem.service.implematation;

import com.yahya.growth.stockmanagementsystem.dao.UserDao;
import com.yahya.growth.stockmanagementsystem.model.User;
import com.yahya.growth.stockmanagementsystem.model.security.Role;
import com.yahya.growth.stockmanagementsystem.service.RoleService;
import com.yahya.growth.stockmanagementsystem.service.UserRoleService;
import com.yahya.growth.stockmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final RoleService roleService;

    @Autowired
    public UserServiceImpl(UserDao userDao, UserRoleService userRoleService, RoleService roleService) {
        this.userDao = userDao;
        this.roleService = roleService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("%s's user details not found", username)));
    }

    @Override
    public User findById(int id) {
        User user =userDao.findById(id).orElseThrow();
        user.setRole(user.getUserRole().getRole());
        return user;
    }

    @Override
    @Transactional
    public User save(User item) {
        return userDao.save(item);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findUserByUsername(username).orElseThrow();
    }

    @Override
    @Transactional
    public User saveUserWithRoles(User user, Role role) {
        user = save(user);
        role = roleService.findById(role.getId());
        user.getUserRole().setUser(user);
        user.getUserRole().setRole(role);
        user.getAuthorities().clear();
        user.getAuthorities().addAll(role.getAuthorities());
        return save(user);
    }

    @Override
    @Transactional
    public User saveNewUser(User user) {
        user.getProfile().setUser(user);
        return saveUserWithRoles(user, user.getRole());
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public boolean deleteById(Integer id) {
        userDao.deleteById(id);
        return true;
    }
}
