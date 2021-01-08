package com.yahya.growth.stockmanagementsystem.security.auth;

import com.google.common.collect.Lists;
import com.yahya.growth.stockmanagementsystem.security.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("fake")
public class FakeApplicationDaoService implements ApplicationServiceDao{

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public FakeApplicationDaoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return getApplicationUsers().stream()
                .filter(applicationUser -> applicationUser.getUsername().equals(username))
                .findFirst();
    }


    private List<ApplicationUser> getApplicationUsers() {
        return Lists.newArrayList(
                new ApplicationUser(
                        "newstaff",
                        passwordEncoder.encode("123"),
                        UserRole.NEW_STAFF.getGrantedAuthorities(),
                        true, true, true,true
                ),
                new ApplicationUser(
                        "staff",
                        passwordEncoder.encode("123"),
                        UserRole.STAFF.getGrantedAuthorities(),
                        true, true, true,true
                ),
                new ApplicationUser(
                        "manager",
                        passwordEncoder.encode("123"),
                        UserRole.MANAGER.getGrantedAuthorities(),
                        true, true, true,true
                ),
                new ApplicationUser(
                        "it",
                        passwordEncoder.encode("123"),
                        UserRole.IT_PERSON.getGrantedAuthorities(),
                        true, true, true,true
                )
        );
    }
}
