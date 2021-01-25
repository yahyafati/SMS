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
    private Boolean fromBeginning = true;
    private LocalDate finalDate;
    private Boolean toLastDate = true;
    private TransactionType type;
    private Boolean bothTypes = true;
    private Customer customer = new Customer();
    private Item item = new Item();
    private User user = new User();

}
