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

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final ItemService itemService;
    private final CustomerService customerService;

    @Autowired
    public OrderController(OrderService orderService, ItemService itemService, CustomerService customerService) {
        this.orderService = orderService;
        this.itemService = itemService;
        this.customerService = customerService;
    }

    @ModelAttribute("title")
    public String getPageTitle() {
        return "Order";
    }

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
        model.addAttribute("items", itemService.findAllAvailableItems());
        model.addAttribute("customers", customerService.findAll());
        return "order/edit";
    }

    @PostMapping("/new")
    public String addOrderPOST(@ModelAttribute Order order) {
        order = orderService.save(order);
        order.setOrderedTime(new Timestamp(System.currentTimeMillis()));
        order.setLastModifiedTime(new Timestamp(System.currentTimeMillis()));
        return "redirect:/order/" + order.getId();
    }

    @GetMapping("/togglePay")
    public String togglePay(@RequestParam int id) {
        orderService.togglePay(id);
        return "redirect:/order/" + id;
    }
//    @GetMapping("/edit")
//    public String editOrder(@RequestParam int id, Model model) {
//        model.addAttribute("order", orderService.findById(id));
//        model.addAttribute("action", "edit");
//        model.addAttribute("items", itemService.findAllAvailableItems());
//        model.addAttribute("customers", customerService.findAll());
//        return "order/edit";
//    }
//
//    @PostMapping("/edit")
//    public String editOrderPOST(@RequestParam int id, @ModelAttribute Order order) {
//        order.setId(id);
//        order = orderService.save(order);
//        order.setLastModifiedTime(new Timestamp(System.currentTimeMillis()));
//        return "redirect:/order/" + id;
//    }

    @GetMapping("/delete")
    public String delete(@RequestParam int id) {
        orderService.deleteById(id);
        return "redirect:/order";
    }
}
