package com.yahya.growth.stockmanagementsystem.controller;

import com.yahya.growth.stockmanagementsystem.model.Customer;
import com.yahya.growth.stockmanagementsystem.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("")
    public String customers(Model model) {
        model.addAttribute("customers", customerService.findAll());
        return "customer/all";
    }

    @GetMapping("/{customerId}")
    public String getCustomer(@PathVariable int customerId, Model model) {
        model.addAttribute("customer", customerService.findById(customerId));
        return "customer/detail";
    }

    @GetMapping("/new")
    public String addCustomer(Model model) {
        model.addAttribute("customer", new Customer());
        model.addAttribute("action", "new");
        return "customer/edit";
    }

    @PostMapping("/new")
    public String addCustomerPOST(@ModelAttribute Customer customer) {
        customer = customerService.save(customer);
        return "redirect:/customer/" + customer.getId();
    }

    @GetMapping("/edit")
    public String editCustomer(@RequestParam int id, Model model) {
        model.addAttribute("customer", customerService.findById(id));
        model.addAttribute("action", "edit");
        return "customer/edit";
    }

    @PostMapping("/edit")
    public String editCustomerPOST(@RequestParam int id, @ModelAttribute Customer customer) {
        customer.setId(id);
        customer = customerService.save(customer);
        return "redirect:/customer/" + id;
    }

    @GetMapping("/delete")
    public String delete(@RequestParam int id) {
        customerService.deleteById(id);
        return "redirect:/customer";
    }


}
