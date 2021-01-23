package com.yahya.growth.stockmanagementsystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Item {

    /*
        Do not use CascadeType.REMOVE
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private int id;
    @Column(name = "item_name")
    private String name;
    @Column(name = "item_description")
    private String description;
    @Column(name = "item_reorderNumber")
    private int reorderNumber;
    @Transient
    private int quantity;

    // TODO Remove Category and get Category from Subcategory
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_subcategory_id")
    private Subcategory subcategory;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_brand_id")
    private Brand brand;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "store_id")
//    private Store store;

//    @OneToMany(mappedBy = "item")
//    @Builder.Default
//    private Set<Order> orders = new HashSet<>();

}
