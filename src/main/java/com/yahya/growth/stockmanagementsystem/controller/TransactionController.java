package com.yahya.growth.stockmanagementsystem.controller;

import com.yahya.growth.stockmanagementsystem.model.Item;
import com.yahya.growth.stockmanagementsystem.model.ItemTransaction;
import com.yahya.growth.stockmanagementsystem.model.Transaction;
import com.yahya.growth.stockmanagementsystem.model.TransactionType;
import com.yahya.growth.stockmanagementsystem.service.CustomerService;
import com.yahya.growth.stockmanagementsystem.service.ItemService;
import com.yahya.growth.stockmanagementsystem.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/transaction")
public class TransactionController implements BasicControllerSkeleton<Transaction> {

    private final TransactionService transactionService;
    private final CustomerService customerService;
    private final ItemService itemService;

    @Autowired
    public TransactionController(TransactionService transactionService, CustomerService customerService, ItemService itemService) {
        this.transactionService = transactionService;
        this.customerService = customerService;
        this.itemService = itemService;
    }

    @Override
    public String index(Model model) {
        return "transaction/all";
    }

    @Override
    @GetMapping("/{id}")
    public String detail(@PathVariable int id, Model model) {
        return "transaction/detail";
    }

    @Override
    @GetMapping("/new")
    public String addNewItem(Model model) {
        model.addAttribute("transaction", new Transaction());
        model.addAttribute("transactionTypes", TransactionType.values());
        model.addAttribute("customers", customerService.findAll());
        model.addAttribute("allItems", itemService.findAll());
        return "transaction/transaction";
    }

    @Override
    public String addNewPOST(Transaction obj) {
        return null;
    }

    @PostMapping("/new")
    public String addNewPOST(Transaction transaction, HttpServletRequest request) {
//        request.getHeaderNames()
        String[] items = request.getParameterValues("item");
        String[] prices = request.getParameterValues("price");
        String[] quantities = request.getParameterValues("quantity");
        transactionService.save(transaction, items, prices, quantities);
        return "redirect:/transaction/new";
    }

    @Override
    public String edit(int id, Model model) {
        return null;
    }

    @Override
    public String editPost(int id, Transaction obj) {
        return null;
    }

    @Override
    public String delete(int id) {
        return null;
    }
}
