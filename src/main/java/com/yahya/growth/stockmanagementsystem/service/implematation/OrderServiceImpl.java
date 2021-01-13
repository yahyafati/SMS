package com.yahya.growth.stockmanagementsystem.service.implematation;

import com.yahya.growth.stockmanagementsystem.dao.OrderDao;
import com.yahya.growth.stockmanagementsystem.model.Item;
import com.yahya.growth.stockmanagementsystem.model.Order;
import com.yahya.growth.stockmanagementsystem.service.ItemService;
import com.yahya.growth.stockmanagementsystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final ItemService itemService;

    public OrderServiceImpl(OrderDao orderDao, ItemService itemService) {
        this.orderDao = orderDao;
        this.itemService = itemService;
    }

    @Override
    public Order findById(int orderId) {
        return orderDao.findById(orderId).orElseThrow();
    }

    @Override
    public Order save(Order order) {
        Item item = itemService.findById(order.getItem().getId());
//        if (order.getQuantity() > item.getQuantity()) {
//            throw new UnsupportedOperationException(String.format("Ordered quantity (%d) is larger than total item in stock (%d)", order.getQuantity(), item.getQuantity()));
//        }
//        item.setQuantity(item.getQuantity() - order.getQuantity());
        itemService.save(item);
        return orderDao.save(order);
    }

    @Override
    public List<Order> findAll() {
        return orderDao.findAll();
    }

    @Override
    public void deleteById(Integer orderId) {
        Order order = findById(orderId);
        if (order.isPaid()) {
            throw new UnsupportedOperationException("This order has already been paid for.");
        }
//        Item item = itemService.findById(order.getItem().getId());
//        item.setQuantity(item.getQuantity() + order.getQuantity());
//        itemService.save(item);
        orderDao.deleteById(orderId);
    }

    @Override
    public Order setPaid(int orderId, boolean value) {
        Order order = findById(orderId);
        return setPaid(order, value);
    }

    @Override
    public Order setPaid(Order order, boolean value) {
        order.setPaid(value);
        return orderDao.save(order);
    }

    @Override
    public Order togglePay(int orderId) {
        Order order = findById(orderId);
        return setPaid(order, !order.isPaid());
    }
}
