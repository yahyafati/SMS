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
    @Column(name = "user_id")
    private int id;
    // Implemented fields
    @Column(name = "user_username", nullable = false, unique = true)
    private String username;
    @Column(name = "user_password")
    private String password;
    @Column(name = "user_is_account_non_expired")
    private boolean isAccountNonExpired = true;
    @Column(name = "user_is_account_non_locked")
    private boolean isAccountNonLocked = true;
    @Column(name = "user_is_credentials_non_expired")
    private boolean isCredentialsNonExpired = true;
    @Column(name = "user_is_enabled")
    private boolean isEnabled = true;

    // Custom Fields
    @ToString.Exclude @EqualsAndHashCode.Exclude @JsonIgnore
    @Builder.Default
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_user_profile_id")
    private UserProfile profile = new UserProfile();

    @ToString.Exclude @EqualsAndHashCode.Exclude @JsonIgnore
    @Builder.Default
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_user_role_id")
    private UserRole userRole = new UserRole();

    @Transient
    @Builder.Default
    private Role role = new Role();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JsonIgnore @EqualsAndHashCode.Exclude @ToString.Exclude
    @Builder.Default
    private Set<Authority> authorities = new HashSet<>();


    public void addAuthority(Authority authority) {
        this.authorities.add(authority);
        authority.getUsers().add(this);
    }

    public void removeAuthority(Authority authority) {
        this.authorities.remove(authority);
        authority.getUsers().remove(this);
    }

}
