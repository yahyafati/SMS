package com.yahya.growth.stockmanagementsystem.model.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Set;

@Data
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    // Implemented fields
    private String username;
    private String password;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;

    // Custom Fields
    private String firstName;
    private Timestamp creationTime;
    private String lastName;
    private String email;
    private String phone;
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore @EqualsAndHashCode.Exclude @ToString.Exclude
    private Set<Authority> authorities;

    public User(String username, String password, Set<Authority> authorities) {
        this(username, password, authorities, true, true, true, true);
    }

    public User(String username, String password, Set<Authority> authorities, boolean isAccountNonExpired, boolean isAccountNonLocked, boolean isCredentialsNonExpired, boolean isEnabled) {
        this.password = password;
        this.username = username;
        this.authorities = authorities;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
        this.isEnabled = isEnabled;
    }

    public User() { }

    public void addAuthority(Authority authority) {
        this.authorities.add(authority);
        authority.getUsers().add(this);
    }

    public void removeAuthority(Authority authority) {
        this.authorities.remove(authority);
        authority.getUsers().remove(this);
    }

    @Override
    public Set<Authority> getAuthorities() {
        return authorities;
    }
}
