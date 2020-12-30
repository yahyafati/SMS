package com.yahya.growth.stockmanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Subcategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @EqualsAndHashCode.Exclude
    private Category category = new Category();

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE}, mappedBy = "subcategory")
    @JsonIgnore @EqualsAndHashCode.Exclude @ToString.Exclude
    private Set<Item> items = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "subcategories")
    @JsonIgnore @EqualsAndHashCode.Exclude @ToString.Exclude
    private Set<Brand> brands = new HashSet<>();

    public void addBrand(Brand brand) {
        this.brands.add(brand);
        brand.getSubcategories().add(this);
    }

    public void removeBrand(Brand brand) {
        this.brands.remove(brand);
        brand.getSubcategories().remove(this);
    }

    @PreRemove
    private void removeAllBrands() {
        this.brands.forEach(this::removeBrand);
    }
}
