package com.yahya.growth.stockmanagementsystem.controller;

import com.google.common.collect.Lists;
import com.yahya.growth.stockmanagementsystem.model.Customer;
import com.yahya.growth.stockmanagementsystem.model.Item;
import com.yahya.growth.stockmanagementsystem.service.CustomerService;
import com.yahya.growth.stockmanagementsystem.utilities.FlashMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @ModelAttribute("title")
    public String getPageTitle() {
        return "Customers";
    }

    @ModelAttribute("active")
    public List<String> getCurrentlyActive() {
        return Lists.newArrayList("customer");
    }

    @GetMapping("")
    public String customers(Model model) {
        model.addAttribute("customers", customerService.findAll());
//        return "customer/all";
        model.addAttribute("pageName", "customer/all");
        return "common/header";
    }

    @GetMapping("/{customerId}")
    public String getCustomer(@PathVariable int customerId, Model model) {
        model.addAttribute("customer", customerService.findById(customerId));
//        return "customer/detail";
        model.addAttribute("pageName", "customer/detail");
        return "common/header";}

    @GetMapping("/new")
    @PreAuthorize("hasAuthority('customer:write')")
    public String addCustomer(Model model) {
        model.addAttribute("customer", new Customer());
        model.addAttribute("action", "new");
//        return "customer/edit";
        model.addAttribute("pageName", "customer/edit");
        return "common/header";
    }

    @PostMapping("/new")
    @PreAuthorize("hasAuthority('customer:write')")
    public String addCustomerPOST(@ModelAttribute Customer customer) {
        customer = customerService.save(customer);
        return "redirect:/customer/" + customer.getId();
    }

    @GetMapping("/edit")
    @PreAuthorize("hasAuthority('customer:write')")
    public String editCustomer(@RequestParam int id, Model model) {
        model.addAttribute("customer", customerService.findById(id));
        model.addAttribute("action", "edit");
//        return "customer/edit";
        model.addAttribute("pageName", "customer/edit");
        return "common/header";
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('customer:write')")
    public String editCustomerPOST(@RequestParam int id, @ModelAttribute Customer customer) {
        customer.setId(id);
        customer = customerService.save(customer);
        return "redirect:/customer/" + id;
    }

    @GetMapping("/toggleStatus")
    @PreAuthorize("hasAuthority('item:write')")
    public RedirectView toggleStatus(@RequestParam int id, @RequestParam String redirectTo) {
        RedirectView redirectView = new RedirectView(redirectTo);
        customerService.toggleStatus(id);
        return redirectView;
    }

    @GetMapping("/delete")
    @PreAuthorize("hasAuthority('customer:write')")
    public RedirectView delete(@RequestParam int id, RedirectAttributes redir) {
        RedirectView redirectView = new RedirectView("/customer", true);
        try {
            customerService.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            Customer customer = customerService.findById(id);
            FlashMessage flashMessage;
            if (customer.isActive()) {
                flashMessage = new FlashMessage("This Customer can not be deleted. Do you want to deactivate it instead?",
                        String.format("/customer/toggleStatus?id=%d&redirectTo=/customer", id), FlashMessage.Type.CONFIRM);
            } else {
                flashMessage = new FlashMessage("This Customer can not be deleted.",
                        String.format("/customer/toggleStatus?id=%d&redirectTo=/customer", id), FlashMessage.Type.ERROR);
            }
            redir.addFlashAttribute("dialogFlash", flashMessage);
        }
        return redirectView;
    }


}
