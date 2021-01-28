package com.yahya.growth.stockmanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private int id;
    @Column(name = "transaction_refNumber")
    private int refNumber;
    @ManyToOne
    @JoinColumn(name = "transaction_customer_id")
    private Customer customer;
    @Column(name = "transaction_addedTime")
    private Timestamp addedTime; // for logging purpose and hidden from end-users
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "transaction_transactionDate")
    private LocalDate transactionDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    private TransactionType type;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore @EqualsAndHashCode.Exclude @ToString.Exclude
    private List<ItemTransaction> itemTransactions = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "transaction_user_id")
    private User user; // for logging purpose, username of the user who added this transaction

    public String getFormattedAddedTime() {
        return new Date(addedTime.getTime()).toString();
    }

    public String getFormattedTransactionDate() {
        return this.transactionDate.format(DateTimeFormatter.ofPattern("dd LLL. yyyy"));
//        return new SimpleDateFormat("dd LLL. yyyy").format(new Date());
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
        return getTotalPrice() - credits.stream()
                .mapToDouble(Credit::getInitialAmount)
                .sum();
    }

    public Double getCreditDifference() {
        return getTotalPrice() - getPaidAmount();
    }
}
