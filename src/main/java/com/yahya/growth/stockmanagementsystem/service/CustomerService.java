package com.yahya.growth.stockmanagementsystem.service;

import com.yahya.growth.stockmanagementsystem.model.Customer;

import java.util.List;

public interface CustomerService {

    Customer findById(int customerId);

    Customer save(Customer customer);

    List<Customer> findAll();

    void deleteById(Integer customerId);

    boolean toggleStatus(int id);

    boolean toggleStatus(Customer customer);
}
