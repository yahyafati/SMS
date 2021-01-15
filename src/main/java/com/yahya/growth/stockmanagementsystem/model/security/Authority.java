package com.yahya.growth.stockmanagementsystem.model.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yahya.growth.stockmanagementsystem.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@Entity
public class Authority implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "authorities", fetch = FetchType.LAZY)
    @JsonIgnore @EqualsAndHashCode.Exclude @ToString.Exclude
    private Set<User> users = new HashSet<>();

    public void addUser(User user) {
        this.users.add(user);
        user.getAuthorities().add(this);
    }

    public void removeUser(User user) {
        this.users.remove(user);
        user.getAuthorities().remove(this);
    }

    public Authority(String name) {
        this.name = name;
    }
    public Authority(int id) {
        this.id = id;
    }
    public Authority() {}

    @Override
    public String getAuthority() {
        // The same as getName()
        return getName();
    }
}

