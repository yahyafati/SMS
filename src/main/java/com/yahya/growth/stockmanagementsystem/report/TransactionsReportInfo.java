package com.yahya.growth.stockmanagementsystem.report;

import com.yahya.growth.stockmanagementsystem.model.Customer;
import com.yahya.growth.stockmanagementsystem.model.Item;
import com.yahya.growth.stockmanagementsystem.model.TransactionType;
import com.yahya.growth.stockmanagementsystem.model.User;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionsReportInfo {

    private LocalDate startDate;
    private Boolean fromBeginning;
    private LocalDate finalDate;
    private Boolean toLastDate;
    private TransactionType transactionType;
    private Customer customer;
    private Item item;
    private User user;

}
