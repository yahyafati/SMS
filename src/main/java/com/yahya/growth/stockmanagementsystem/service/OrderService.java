package com.yahya.growth.stockmanagementsystem.service;

import com.yahya.growth.stockmanagementsystem.model.Order;

import java.util.List;

public interface OrderService {

    Order findById(int orderId);

    Order save(Order order);

    List<Order> findAll();

    void deleteById(Integer orderId);
}
