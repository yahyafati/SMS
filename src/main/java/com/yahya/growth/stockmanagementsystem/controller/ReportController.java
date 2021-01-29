package com.yahya.growth.stockmanagementsystem.controller;

import com.yahya.growth.stockmanagementsystem.model.Customer;
import com.yahya.growth.stockmanagementsystem.model.Item;
import com.yahya.growth.stockmanagementsystem.model.TransactionType;
import com.yahya.growth.stockmanagementsystem.model.User;
import com.yahya.growth.stockmanagementsystem.report.TransactionsReportInfo;
import com.yahya.growth.stockmanagementsystem.service.CustomerService;
import com.yahya.growth.stockmanagementsystem.service.ItemService;
import com.yahya.growth.stockmanagementsystem.service.ReportService;
import com.yahya.growth.stockmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;

@Controller
@RequestMapping("/reports")
public class ReportController {

    private final ItemService itemService;
    private final CustomerService customerService;
    private final ReportService reportService;
    private final UserService userService;

    @Autowired
    public ReportController(ItemService itemService, CustomerService customerService, ReportService reportService, UserService userService) {
        this.itemService = itemService;
        this.customerService = customerService;
        this.reportService = reportService;
        this.userService = userService;
    }

    private ResponseEntity<InputStreamResource> createResponseEntity(byte[] reportStreamBytes, String name) {
        ByteArrayInputStream stream = new ByteArrayInputStream(reportStreamBytes);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", String.format("inline; filename=%s.pdf", name));
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(stream));
    }


    @ModelAttribute("title")
    public String getPageTitle() {
        return "Reports";
    }

    @ModelAttribute("items")
    public List<Item> getAllItems() {
        return itemService.findAll();
    }

    @ModelAttribute("customers")
    public List<Customer> getAllCustomers() {
        return customerService.findAll();
    }

    @ModelAttribute("users")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @ModelAttribute("transactionTypes")
    public TransactionType[] getAllTransactionTypes() {
        return TransactionType.values();
    }

    @GetMapping("/transaction")
    public String getTransactionReport(Model model) {
        model.addAttribute("transactionReport", new TransactionsReportInfo());
        model.addAttribute("pageName", "report/transaction");
        model.addAttribute("action", "");
        return "common/header";
    }

    @GetMapping("/bytype")
    public String getItemTransactionsByType(Model model) {
        model.addAttribute("transactionReport", new TransactionsReportInfo());
        model.addAttribute("pageName", "report/transaction");
        model.addAttribute("action", "byType");
        return "common/header";
    }

    @GetMapping("/summary")
    public String getItemTransactionsSummary(Model model) {
        model.addAttribute("transactionReport", new TransactionsReportInfo());
        model.addAttribute("pageName", "report/transaction");
        model.addAttribute("action", "summary");
        return "common/header";
    }

    @GetMapping(value = "/invoice", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getTransactionsInvoice(@RequestParam Integer id, Model model) {
        final byte[] bytes = reportService.generateInvoice(id);
        return createResponseEntity(bytes, "Invoice");
    }

    @PostMapping(value = "/byType", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getItemTransactionsByTypePOST(TransactionsReportInfo transactionsReportInfo) {
        final byte[] bytes = reportService.generateTransactionReportByType(transactionsReportInfo);
        return createResponseEntity(bytes, "ItemTransactionReportByType");
    }

    @PostMapping(value = "/transaction", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getTransactionReportPOST(TransactionsReportInfo transactionsReportInfo) {
        final byte[] bytes = reportService.generateTransactionReport(transactionsReportInfo);
        return createResponseEntity(bytes, "TransactionReport");
    }

    @PostMapping(value = "/summary", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getItemTransactionsSummaryPOST(TransactionsReportInfo transactionsReportInfo) {
        final byte[] bytes = reportService.generateItemTransactionSummaryReport(transactionsReportInfo);
        return createResponseEntity(bytes, "TransactionReport");
    }




}
