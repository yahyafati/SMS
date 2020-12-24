package com.yahya.growth.stockmanagementsystem.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class SubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Category category;

//    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<Item> itemList;

}
