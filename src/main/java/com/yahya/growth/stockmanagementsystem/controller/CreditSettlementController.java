package com.yahya.growth.stockmanagementsystem.controller;

import com.yahya.growth.stockmanagementsystem.model.Customer;
import com.yahya.growth.stockmanagementsystem.model.Settlement;
import com.yahya.growth.stockmanagementsystem.service.CreditService;
import com.yahya.growth.stockmanagementsystem.service.CustomerService;
import com.yahya.growth.stockmanagementsystem.service.SettlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/credit")
public class CreditSettlementController {

    private final CreditService creditService;
    private final CustomerService customerService;
    private final SettlementService settlementService;

    @Autowired
    public CreditSettlementController(CreditService creditService, CustomerService customerService, SettlementService settlementService) {
        this.creditService = creditService;
        this.customerService = customerService;
        this.settlementService = settlementService;
    }

    @ModelAttribute("title")
    public String getPageTitle() {
        return "Credits";
    }

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("payableMap", creditService.findAllPayablePerCustomer());
        model.addAttribute("receivableMap", creditService.findAllReceivablePerCustomer());

        model.addAttribute("pageName", "credit/all");
        return "common/header";
    }

    @GetMapping("/settle")
    public String settlePayable(@RequestParam(defaultValue = "0") int id, Model model) {
        Customer customer = new Customer(id);
        System.out.println(customer);
        Settlement settlement = new Settlement();
        settlement.setCustomer(customer);

//        model.addAttribute("customer", customer);
        model.addAttribute("allCustomers", customerService.findAll());
        model.addAttribute("customerCredits", creditService.findByCustomer(customer));
        model.addAttribute("settlement", settlement);
        model.addAttribute("pageName", "credit/settle");
        return "common/header";
    }

    @PostMapping("/settle")
    public String settlePayablePOST(Settlement settlement) {
        settlement = settlementService.saveNewSettlement(settlement);
        return "redirect:/credit/";
    }

    public String detail(int id, Model model) {
        return null;
    }

    public String delete(int id) {
        return null;
    }
}
