package com.yahya.growth.stockmanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@Builder
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;

    private String email;
    private String phone;

    // TODO Make this address field atomic
    private String address;

    @OneToMany(mappedBy = "supplier", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @JsonIgnore
    @EqualsAndHashCode.Exclude @ToString.Exclude
    @Builder.Default
    private Set<Item> items = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Category> categories = new HashSet<>();

    public Supplier() {}
}
