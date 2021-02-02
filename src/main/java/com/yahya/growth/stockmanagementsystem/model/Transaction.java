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
    @Column(name = "transaction_ref_number")
    private int refNumber;
    @ManyToOne
    @JoinColumn(name = "transaction_customer_id")
    private Customer customer;
    @Column(name = "transaction_added_time")
    private Timestamp addedTime; // for logging purpose and hidden from end-users
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "transaction_transaction_date")
    private LocalDate transactionDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    private TransactionType type;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore @EqualsAndHashCode.Exclude @ToString.Exclude
    private Set<ItemTransaction> itemTransactions = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "transaction_user_id")
    private User user = new User(); // for logging purpose, username of the user who added this transaction

    @Column(name = "transaction_vat")
    private double vat = 0.0;
    @Column(name = "transaction_service_charge")
    private double serviceCharge = 0.0;
    @Column(name = "transaction_discount")
    private double discount = 0.0;

    public String getFormattedAddedTime() {
        return new Date(addedTime.getTime()).toString();
    }

    public String getFormattedTransactionDate() {
        return this.transactionDate.format(DateTimeFormatter.ofPattern("dd LLL. yyyy"));
    }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "transaction")
    @JsonIgnore @EqualsAndHashCode.Exclude @ToString.Exclude
    public Set<Credit> credits = new HashSet<>();

    public Double getOverallPrice() {
        double price = getTotalPrice();
        price += (price*vat/100.0) + (price*serviceCharge/100.0) - (price*discount/100.0);
        return price;
    }

    public Double getTotalPrice() {
        return itemTransactions.stream()
                .mapToDouble(ItemTransaction::getTotalPrice)
                .sum();
    }

    public Double getPaidAmount() {
        return getTotalPrice() - credits.stream()
                .mapToDouble(Credit::getInitialAmount)
                .sum();
    }

    public Double getCreditDifference() {
        return getTotalPrice() - getPaidAmount();
    }
}
