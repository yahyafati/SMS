package com.yahya.growth.stockmanagementsystem.controller;

import com.yahya.growth.stockmanagementsystem.service.CompanyService;
import com.yahya.growth.stockmanagementsystem.model.utility.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("")
    public String company(Model model) {
        Company thisCompany = companyService.getCurrentCompany();
        model.addAttribute("action", "");
        model.addAttribute("company", thisCompany);
        return "company/info";
    }

    @PostMapping("")
    public String companyPOST(@ModelAttribute Company company) {
        companyService.saveCurrentCompany(company);
        return "redirect:/company";
    }

}
