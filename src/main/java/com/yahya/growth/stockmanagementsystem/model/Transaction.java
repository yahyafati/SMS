package com.yahya.growth.stockmanagementsystem.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private int refNumber;
    @ManyToOne
    private Customer fromTo;
    private Timestamp addedTime; // for logging purpose and hidden from end-users
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate transactionDate;
    private TransactionType type;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ItemTransaction> itemTransactions = new ArrayList<>();
    @ManyToOne
    private User user; // for logging purpose, username of the user who added this transaction

    public String getFormattedAddedTime() {
        return new Date(addedTime.getTime()).toString();
    }

}
