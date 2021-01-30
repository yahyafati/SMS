package com.yahya.growth.stockmanagementsystem.controller;

import com.yahya.growth.stockmanagementsystem.model.CreditType;
import com.yahya.growth.stockmanagementsystem.model.Customer;
import com.yahya.growth.stockmanagementsystem.model.Settlement;
import com.yahya.growth.stockmanagementsystem.model.SettlementType;
import com.yahya.growth.stockmanagementsystem.service.CreditService;
import com.yahya.growth.stockmanagementsystem.service.CustomerService;
import com.yahya.growth.stockmanagementsystem.service.SettlementService;
import com.yahya.growth.stockmanagementsystem.utilities.CreditException;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    // TODO Remove Sneaky Throws
    @SneakyThrows
    @GetMapping("")
    public String index(@RequestParam(value = "type", defaultValue = "payable") String type, Model model) {
        if (type.equalsIgnoreCase("payable")) {
            model.addAttribute("creditsMap", creditService.findAllPayablePerCustomer());
        } else if(type.equalsIgnoreCase("receivable")) {
            model.addAttribute("creditsMap", creditService.findAllReceivablePerCustomer());
        } else {
            throw new CreditException("Invalid Parameter");
        }
        model.addAttribute("pageName", "credit/all");
        model.addAttribute("type", StringUtils.capitalize(type));
        return "common/header";
    }

    @GetMapping("/settle")
    @PreAuthorize("hasAuthority('credit:write')")
    public String settle(@RequestParam("type") String type, @RequestParam(defaultValue = "0") int id, Model model) {
        Customer customer = new Customer(id);
        Settlement settlement = new Settlement();
        settlement.setCustomer(customer);
        CreditType creditType =CreditType.getType(type);
        if (creditType == CreditType.BORROWED) {
            settlement.setType(SettlementType.PAID);
            model.addAttribute("customerCredits", creditService.findPayableCreditsByCustomer(customer));
        } else {
            settlement.setType(SettlementType.RECEIVED);
            model.addAttribute("customerCredits", creditService.findReceivableCreditsByCustomer(customer));
        }

        model.addAttribute("allCustomers", customerService.findAll());
        model.addAttribute("settlement", settlement);
        model.addAttribute("pageName", "credit/settle");
        return "common/header";
    }

    @PostMapping("/settle")
    @PreAuthorize("hasAuthority('credit:write')")
    public String settlePayablePOST(Settlement settlement) {
        settlement = settlementService.saveNewSettlement(settlement);
        return "redirect:/credit/";
    }

}
