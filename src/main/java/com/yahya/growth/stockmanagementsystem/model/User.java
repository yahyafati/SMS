package com.yahya.growth.stockmanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yahya.growth.stockmanagementsystem.model.security.Authority;
import com.yahya.growth.stockmanagementsystem.model.security.Role;
import com.yahya.growth.stockmanagementsystem.model.security.UserRole;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    // Implemented fields
    @Column(nullable = false, unique = true)
    private String username;
    private String password;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;

    // Custom Fields
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private UserProfile profile = new UserProfile();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Builder.Default
    private UserRole userRole = new UserRole();

    @Transient
    @Builder.Default
    private Role role = new Role();

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore @EqualsAndHashCode.Exclude @ToString.Exclude
    @Builder.Default
    private Set<Authority> authorities = new HashSet<>();


    // TODO Many to Many Utilities on non InnoDB MySQL database
    public void addAuthority(Authority authority) {
        this.authorities.add(authority);
        authority.getUsers().add(this);
    }

    public void removeAuthority(Authority authority) {
        this.authorities.remove(authority);
        authority.getUsers().remove(this);
    }

}
