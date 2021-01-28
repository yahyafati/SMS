package com.yahya.growth.stockmanagementsystem.controller;

import com.yahya.growth.stockmanagementsystem.model.TransactionType;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayInputStream;

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


    @ModelAttribute("title")
    public String getPageTitle() {
        return "Reports";
    }

    @GetMapping("/transaction")
    public String getTransactionReport(Model model) {
        model.addAttribute("transactionReport", new TransactionsReportInfo());
        model.addAttribute("items", itemService.findAll());
        model.addAttribute("customers", customerService.findAll());
        model.addAttribute("users", userService.findAll());
        model.addAttribute("transactionTypes", TransactionType.values());

        model.addAttribute("pageName", "report/transaction");
        return "common/header";
    }

    @PostMapping(value = "/transaction", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getTransactionReportPOST(TransactionsReportInfo transactionsReportInfo) {
        System.out.println("\n\n" + transactionsReportInfo + "\n\n");
        final byte[] bytes = reportService.generateTransactionReport(transactionsReportInfo);
        ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=TransactionReport.pdf");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(stream));
    }

    @GetMapping(value = "/items", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getItemsReportPOST() {
        final byte[] bytes = reportService.generateItemList();
        ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=ItemsReport.pdf");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(stream));
    }


}
