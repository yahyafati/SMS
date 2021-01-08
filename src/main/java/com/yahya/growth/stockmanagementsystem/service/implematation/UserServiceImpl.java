package com.yahya.growth.stockmanagementsystem.service.implematation;

import com.yahya.growth.stockmanagementsystem.dao.UserDao;
import com.yahya.growth.stockmanagementsystem.model.security.User;
import com.yahya.growth.stockmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("%s's user details not found", username)));
    }

    @Override
    public User findById(int id) {
        return userDao.findById(id).orElseThrow();
    }

    @Override
    public User save(User item) {
        return userDao.save(item);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        userDao.deleteById(id);
    }
}
