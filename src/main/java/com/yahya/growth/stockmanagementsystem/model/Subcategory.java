package com.yahya.growth.stockmanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Subcategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subcategory_id")
    private int id;
    @Column(name = "subcategory_name")
    private String name;
    @Column(name = "subcategory_description")
    private String description;

    @EqualsAndHashCode.Exclude
    @Builder.Default
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "subcategory_category_id")
    private Category category = new Category();

    @JsonIgnore @EqualsAndHashCode.Exclude @ToString.Exclude
    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE}, mappedBy = "subcategory")
    private Set<Item> items = new HashSet<>();

//    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "subcategories")
//    @JsonIgnore @EqualsAndHashCode.Exclude @ToString.Exclude
//    @Builder.Default
//    private Set<Brand> brands = new HashSet<>();

//    public void addBrand(Brand brand) {
//        this.brands.add(brand);
//        brand.getSubcategories().add(this);
//    }
//
//    public void removeBrand(Brand brand) {
//        this.brands.remove(brand);
//        brand.getSubcategories().remove(this);
//    }

//    @PreRemove
//    private void removeAllBrands() {
//        this.brands.forEach(this::removeBrand);
//    }
}
