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
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private int id;
    @Column(name = "category_name")
    private String name;
    @Column(name = "category_description")
    private String description;

    @OneToMany(mappedBy = "category", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore @EqualsAndHashCode.Exclude @ToString.Exclude
    @Builder.Default
    private Set<Subcategory> subcategories = new HashSet<>();


}
