package com.yahya.growth.stockmanagementsystem.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    // TODO Don't Forget to change it to STRING
//    @Enumerated(EnumType.STRING)
    private CreditType type;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Transaction transaction;
    private double settlementAmount;

    public Credit(CreditType type) {
        this.type = type;
    }

}
