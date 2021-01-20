package com.yahya.growth.stockmanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;

@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int refNumber;
    @ManyToOne
    private Customer customer;
    private Timestamp addedTime; // for logging purpose and hidden from end-users
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate transactionDate;
    @Enumerated(EnumType.STRING)
    private TransactionType type;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore @EqualsAndHashCode.Exclude @ToString.Exclude
    private List<ItemTransaction> itemTransactions = new ArrayList<>();
    @ManyToOne
    private User user; // for logging purpose, username of the user who added this transaction

    public String getFormattedAddedTime() {
        return new Date(addedTime.getTime()).toString();
    }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "transaction")
    @JsonIgnore @EqualsAndHashCode.Exclude @ToString.Exclude
    public Set<Credit> credits = new HashSet<>();

    public Double getTotalPrice() {
        return itemTransactions.stream()
                .mapToDouble(ItemTransaction::getTotalPrice)
                .sum();
    }

    public Double getPaidAmount() {
        System.out.println("credits: " + credits);
        return credits.stream()
                .mapToDouble(Credit::getSettlementAmount)
                .sum();
    }

    public Double getCreditDifference() {
        return getTotalPrice() - getPaidAmount();
    }
}
