package com.yahya.growth.stockmanagementsystem.report;

import com.yahya.growth.stockmanagementsystem.model.Customer;
import com.yahya.growth.stockmanagementsystem.model.Item;
import com.yahya.growth.stockmanagementsystem.model.TransactionType;
import com.yahya.growth.stockmanagementsystem.model.User;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class TransactionsReportInfo {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    private Boolean fromBeginning = true;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate finalDate;
    private Boolean toLastDate = true;
    private TransactionType type;
    private Boolean bothTypes = true;
    private Customer customer = new Customer();
    private Item item = new Item();
    private User user = new User();

}
