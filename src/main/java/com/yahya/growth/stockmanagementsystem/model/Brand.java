package com.yahya.growth.stockmanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yahya.growth.stockmanagementsystem.service.implematation.SubcategoryServiceImpl;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@Builder
public class Brand {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    private String name;
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinTable(name = "brand_categories",
            joinColumns = {@JoinColumn(name = "brand_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")})
    @Builder.Default
    private Set<Category> categories = new HashSet<>();

    // TODO TEST in case of ManyToMany
    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinTable(name = "brand_subcategories",
            joinColumns = {@JoinColumn(name = "brand_id")},
            inverseJoinColumns = {@JoinColumn(name = "subcategory_id")})
    @Builder.Default
    private Set<Subcategory> subcategories = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "brand", cascade = CascadeType.REMOVE)
    @JsonIgnore @ToString.Exclude @EqualsAndHashCode.Exclude
    @Builder.Default
    private  Set<Item> items = new HashSet<>();


    public Brand() {}
}
