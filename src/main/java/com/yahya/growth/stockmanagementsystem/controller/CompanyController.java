package com.yahya.growth.stockmanagementsystem.controller;

import com.google.common.collect.Lists;
import com.yahya.growth.stockmanagementsystem.service.CompanyService;
import com.yahya.growth.stockmanagementsystem.model.utility.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @ModelAttribute("title")
    public String getPageTitle() {
        return "Company";
    }

    @ModelAttribute("active")
    public List<String> getCurrentlyActive() {
        return Lists.newArrayList("company", "adminTools");
    }

    @GetMapping("")
    public String company(Model model) {
        Company thisCompany = companyService.getCurrentCompany();
        model.addAttribute("action", "");
        model.addAttribute("company", thisCompany);
//        return "company/info";
        model.addAttribute("pageName", "company/info");
        return "common/header";
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('company:write')")
    public String companyPOST(@ModelAttribute Company company) {
        companyService.saveCurrentCompany(company);
        return "redirect:/company";
    }

}
