package com.yahya.growth.stockmanagementsystem.security.auth;

import com.google.common.collect.Lists;
import com.yahya.growth.stockmanagementsystem.model.security.User;
import com.yahya.growth.stockmanagementsystem.security.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//@Repository("fake")
@Deprecated
public class FakeUserDaoService implements UserServiceDao {

    private final PasswordEncoder passwordEncoder;

//    @Autowired
    public FakeUserDaoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> selectApplicationUserByUsername(String username) {
        return getApplicationUsers().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }


    private List<User> getApplicationUsers() {
        return Lists.newArrayList(
//                new User(
//                        "newstaff",
//                        passwordEncoder.encode("123"),
//                        UserRole.NEW_STAFF.getGrantedAuthorities(),
//                        true, true, true,true
//                ),
//                new User(
//                        "staff",
//                        passwordEncoder.encode("123"),
//                        UserRole.STAFF.getGrantedAuthorities(),
//                        true, true, true,true
//                ),
//                new User(
//                        "manager",
//                        passwordEncoder.encode("123"),
//                        UserRole.MANAGER.getGrantedAuthorities(),
//                        true, true, true,true
//                ),
//                new User(
//                        "it",
//                        passwordEncoder.encode("123"),
//                        UserRole.IT_PERSON.getGrantedAuthorities(),
//                        true, true, true,true
//                )
        );
    }
}
