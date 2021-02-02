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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "brand")
    @JsonIgnore @ToString.Exclude @EqualsAndHashCode.Exclude
    @Builder.Default
    private  Set<Item> items = new HashSet<>();


    public Brand() {}
}
