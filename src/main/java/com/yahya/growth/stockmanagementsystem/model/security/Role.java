package com.yahya.growth.stockmanagementsystem.model.security;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, unique = true)
    private String name;
    @ManyToMany
    @Builder.Default
    private Set<Authority> authorities = new HashSet<>();

    public Role() { }

    public String getDisplayName() {
        String displayName = name.substring(5); // since ROLE_ will be removed from display name
        return StringUtils.capitalize(String.join(" ", displayName.split("_")));
    }
}
