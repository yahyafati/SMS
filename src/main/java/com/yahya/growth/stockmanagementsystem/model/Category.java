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
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;

    @OneToMany(mappedBy = "category", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @JsonIgnore @EqualsAndHashCode.Exclude @ToString.Exclude
    @Builder.Default
    private Set<Item> items = new HashSet<>();

    @OneToMany(mappedBy = "category", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore @EqualsAndHashCode.Exclude @ToString.Exclude
    @Builder.Default
    private Set<Subcategory> subcategories = new HashSet<>();

    // FIXME Change REMOVE Cascades
//    @ManyToMany(mappedBy = "categories", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
//    @JsonIgnore @EqualsAndHashCode.Exclude @ToString.Exclude
//    @Builder.Default
//    private Set<Store> stores = new HashSet<>();
//
//    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "categories")
//    @JsonIgnore @EqualsAndHashCode.Exclude @ToString.Exclude
//    @Builder.Default
//    private Set<Brand> brands = new HashSet<>();

//    public void addBrand(Brand brand) {
//        this.brands.add(brand);
//        brand.getCategories().add(this);
//    }
//
//    public void removeBrand(Brand brand) {
//        this.brands.remove(brand);
//        brand.getCategories().remove(this);
//    }

//    @PreRemove
//    private void removeAllBrands() {
//        this.brands.forEach(this::removeBrand);
//    }

    public Category() {}
}
