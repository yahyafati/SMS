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
public class Brand {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "brand_id")
    private int id;
    @Column(name = "brand_name")
    private String name;
    @Column(name = "brand_description")
    private String description;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JsonIgnore
//    @JoinTable(name = "brand_categories",
//            joinColumns = {@JoinColumn(name = "brand_id")},
//            inverseJoinColumns = {@JoinColumn(name = "category_id")})
//    @Builder.Default
//    private Set<Category> categories = new HashSet<>();
//
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JsonIgnore
//    @JoinTable(name = "brand_subcategories",
//            joinColumns = {@JoinColumn(name = "brand_id")},
//            inverseJoinColumns = {@JoinColumn(name = "subcategory_id")})
//    @Builder.Default
//    private Set<Subcategory> subcategories = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "brand", cascade = CascadeType.REMOVE)
    @JsonIgnore @ToString.Exclude @EqualsAndHashCode.Exclude
    @Builder.Default
    private  Set<Item> items = new HashSet<>();


    public Brand() {}
}
