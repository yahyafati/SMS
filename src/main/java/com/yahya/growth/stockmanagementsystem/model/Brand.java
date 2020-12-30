package com.yahya.growth.stockmanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Brand {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    private String name;
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Category> categorySet = new HashSet<>();

    // TODO TEST in case of ManyToMany
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Subcategory> subcategorySet = new HashSet<>();

}
