package com.yahya.growth.stockmanagementsystem.service;

import com.yahya.growth.stockmanagementsystem.model.Credit;
import com.yahya.growth.stockmanagementsystem.model.Customer;
import com.yahya.growth.stockmanagementsystem.model.Transaction;

import java.util.List;
import java.util.Map;

public interface CreditService extends BasicServiceSkeleton<Credit> {
//


    Map<Customer, Double> findAllPayablePerCustomer();

    Map<Customer, Double> findAllReceivablePerCustomer();

    List<Credit> findByCustomer(Customer customer);
//
    List<Credit> findByTransaction(Transaction transaction);
}
