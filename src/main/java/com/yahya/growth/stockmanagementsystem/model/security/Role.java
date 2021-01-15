package com.yahya.growth.stockmanagementsystem.model.security;

import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Role implements Comparable<Role> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, unique = true)
    private String name;
    @ManyToMany(fetch = FetchType.EAGER)
    @Builder.Default
    private Set<Authority> authorities = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<UserRole> userRole = new HashSet<>();

    public Role() { }

    public String getDisplayName() {
        String displayName = name.substring(5); // since ROLE_ will be removed from display name
        return StringUtils.capitalize(String.join(" ", displayName.split("_")));
    }

    @Override
    public int compareTo(@NotNull Role o) {
        return Integer.compare(id, o.id);
    }
}
