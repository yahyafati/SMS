package com.yahya.growth.stockmanagementsystem.service.implematation;

import com.yahya.growth.stockmanagementsystem.dao.security.AuthorityDao;
import com.yahya.growth.stockmanagementsystem.model.security.Authority;
import com.yahya.growth.stockmanagementsystem.service.AuthorityService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityDao authorityDao;

    @Autowired
    public AuthorityServiceImpl(AuthorityDao authorityDao) {
        this.authorityDao = authorityDao;
    }

    @Override
    public Authority findById(int id) {
        return authorityDao.findById(id).orElseThrow();
    }

    @Override
    public Authority save(Authority item) {
        return authorityDao.save(item);
    }

    @Override
    public List<Authority> findAll() {
        return authorityDao.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        authorityDao.deleteById(id);
    }

    @Override
    @SneakyThrows
    public Authority findByName(String authorityName) {
        Optional<Authority> authority = authorityDao.findAuthorityByName(authorityName);
//        System.out.println(authority);
        return authority.orElseThrow(() -> new AuthorityNotFoundException(String.format("There is no authority with the name: %s ", authorityName)));
    }

}

class AuthorityNotFoundException extends Exception {

    public AuthorityNotFoundException(String message) {
        super(message);
    }
}