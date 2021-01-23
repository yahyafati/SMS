package com.yahya.growth.stockmanagementsystem.controller;

import com.yahya.growth.stockmanagementsystem.model.*;
import com.yahya.growth.stockmanagementsystem.service.CustomerService;
import com.yahya.growth.stockmanagementsystem.service.ItemService;
import com.yahya.growth.stockmanagementsystem.service.ItemTransactionService;
import com.yahya.growth.stockmanagementsystem.service.TransactionService;
import com.yahya.growth.stockmanagementsystem.utilities.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    @ModelAttribute("title")
    public String getPageTitle() {
        return "Transaction";
    }

    @ModelAttribute("transactionTypes")
    public TransactionType[] getTransactionTypes() {
        return TransactionType.values();
    }

    @ModelAttribute("customers")
    public List<Customer> getCustomers() {
        return customerService.findAll();
    }

    @ModelAttribute("allItems")
    public List<Item> getItems() {
        return itemService.findAll();
    }

    @Override
    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("transactions", transactionService.findAll());
//        return "transaction/all";
        model.addAttribute("pageName", "transaction/all");
        return "common/header";
    }

    @Override
    @GetMapping("/{id}")
    public String detail(@PathVariable int id, Model model) {
        Transaction transaction = transactionService.findById(id);

        model.addAttribute("transaction", transaction);
//        return "transaction/detail";
        model.addAttribute("pageName", "transaction/detail");
        return "common/header";
    }

    @Override
    @GetMapping("/new")
    public String addNewItem(Model model) {
        model.addAttribute("transaction", new Transaction());
        model.addAttribute("transactionTypes", TransactionType.values());
        model.addAttribute("customers", customerService.findAll());
        model.addAttribute("allItems", itemService.findAll());
        model.addAttribute("action", "new");
//        return "transaction/transaction";
        model.addAttribute("pageName", "transaction/transaction");
        return "common/header";
    }

    @GetMapping("/purchase")
    public String purchaseItem(Model model) {
        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.PURCHASE);
        model.addAttribute("transaction", transaction);
        model.addAttribute("paidAmount", 0.0);
        model.addAttribute("action", "new");
        model.addAttribute("pageName", "transaction/transaction");
        return "common/header";
    }

    @GetMapping("/sale")
    public String saleItem(Model model) {
        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.SALE);
        model.addAttribute("transaction", transaction);
        model.addAttribute("action", "new");
        model.addAttribute("pageName", "transaction/transaction");
        return "common/header";
    }

    @Override
    public String addNewPOST(Transaction obj) {
        return null;
    }

    @PostMapping("/new")
    public String addNewPOST(Transaction transaction, Double paidAmount, HttpServletRequest request) throws TransactionException {
        String[] ids = request.getParameterValues("transactionID");
        String[] items = request.getParameterValues("item");
        String[] prices = request.getParameterValues("price");
        String[] quantities = request.getParameterValues("quantity");
        transaction = transactionService.save(transaction, ids, items, prices, quantities, paidAmount);
        return "redirect:/transaction/" + transaction.getId();
    }

    @Override
    @GetMapping("/edit")
    public String edit(@RequestParam int id, Model model) {
        Transaction transaction = transactionService.findById(id);
        model.addAttribute("transaction", transaction);
        model.addAttribute("transactionTypes", TransactionType.values());
        model.addAttribute("customers", customerService.findAll());
        model.addAttribute("allItems", itemService.findAll());
        model.addAttribute("action", "edit");
        // FIXME Get the amount paid on this credit
        model.addAttribute("paidAttribute", transaction.getCreditDifference());
//        return "transaction/transaction";
        model.addAttribute("pageName", "transaction/transaction");
        return "common/header";
    }

    @PostMapping("/edit")
    public String editPOST(Transaction transaction, Double paidAmount, HttpServletRequest request) throws TransactionException {
//        request.getHeaderNames()
        // Repeated Code with add new post
        String[] items = request.getParameterValues("item");
        String[] prices = request.getParameterValues("price");
        String[] quantities = request.getParameterValues("quantity");
        String[] ids = request.getParameterValues("transactionID");
        transactionService.save(transaction, ids, items, prices, quantities, paidAmount);
        return "redirect:/transaction";
    }


    @Override
    public String editPost(int id, Transaction obj) {
        return null;
    }

    @Override
    @GetMapping("/delete")
    public String delete(@RequestParam int id) {
        transactionService.deleteById(id);
        return "redirect:/transaction";
    }
}
