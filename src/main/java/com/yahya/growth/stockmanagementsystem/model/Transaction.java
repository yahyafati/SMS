package com.yahya.growth.stockmanagementsystem.model;

import com.yahya.growth.stockmanagementsystem.model.security.User;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private TransactionType type;
    @OneToMany
    private Set<ItemTransaction> orders = new HashSet<>();
    @ManyToOne
    private User user;

}
