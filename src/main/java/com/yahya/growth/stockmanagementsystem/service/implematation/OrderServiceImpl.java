package com.yahya.growth.stockmanagementsystem.service.implematation;

import com.yahya.growth.stockmanagementsystem.dao.OrderDao;
import com.yahya.growth.stockmanagementsystem.model.Order;
import com.yahya.growth.stockmanagementsystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Override
    public Order findById(int orderId) {
        return orderDao.findById(orderId).orElseThrow();
    }

    @Override
    public Order save(Order order) {
        return orderDao.save(order);
    }

    @Override
    public List<Order> findAll() {
        return orderDao.findAll();
    }

    @Override
    public void deleteById(Integer orderId) {
        orderDao.deleteById(orderId);
    }
}
