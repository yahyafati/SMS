package com.yahya.growth.stockmanagementsystem.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Item> itemList;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<SubCategory> subCategoryList;

//    @JoinTable(name = "category_subcategory",
//            joinColumns = {@JoinColumn(name = "category_id")},
//            inverseJoinColumns = {@JoinColumn(name = "subcategory_id")}
//    )
}
