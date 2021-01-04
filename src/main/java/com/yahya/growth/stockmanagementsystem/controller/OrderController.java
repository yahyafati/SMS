package com.yahya.growth.stockmanagementsystem.controller;

import com.yahya.growth.stockmanagementsystem.model.Order;
import com.yahya.growth.stockmanagementsystem.service.CustomerService;
import com.yahya.growth.stockmanagementsystem.service.ItemService;
import com.yahya.growth.stockmanagementsystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private CustomerService customerService;


    @GetMapping("")
    public String orders(Model model) {
        model.addAttribute("orders", orderService.findAll());
        return "order/all";
    }

    @GetMapping("/{orderId}")
    public String getOrder(@PathVariable int orderId, Model model) {
        model.addAttribute("order", orderService.findById(orderId));
        return "order/detail";
    }

    @GetMapping("/new")
    public String addOrder(Model model) {
        model.addAttribute("order", new Order());
        model.addAttribute("action", "new");
        model.addAttribute("items", itemService.findAll());
        model.addAttribute("customers", customerService.findAll());
        return "order/edit";
    }

    @PostMapping("/new")
    public String addOrderPOST(@ModelAttribute Order order) {
        order = orderService.save(order);
        order.setOrderedTime(new Timestamp(System.currentTimeMillis()));
        return "redirect:/order/" + order.getId();
    }
}
