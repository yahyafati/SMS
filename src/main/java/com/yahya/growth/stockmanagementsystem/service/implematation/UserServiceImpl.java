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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, UserRoleService userRoleService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("%s's user details not found", username)));
    }

    @Override
    public User findById(int id) {
        User user =userDao.findById(id).orElseThrow(() -> {throw new IllegalArgumentException("The Item you are looking for is no longer available.");});
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
        return userDao.findUserByUsername(username).orElseThrow(() -> {throw new UsernameNotFoundException("The Item you are looking for is no longer available.");});
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
    public boolean checkIfPasswordIsValid(String username, String password) {
        User user = findByUsername(username);
        return checkIfPasswordIsValid(user, password);
    }

    @Override
    public boolean checkIfPasswordIsValid(User user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }

    @Override
    @Transactional
    public User saveNewUser(User user) {
        user.getProfile().setUser(user);
        return saveUserWithRoles(user, user.getRole());
    }

    @Override
    public User changePassword(String username, String newPassword) {
        User user = findByUsername(username);
        return changePassword(user, newPassword);
    }

    @Override
    public User changePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        return save(user);
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
