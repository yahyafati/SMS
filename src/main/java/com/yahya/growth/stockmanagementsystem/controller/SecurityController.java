package com.yahya.growth.stockmanagementsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class SecurityController {

    @ModelAttribute("title")
    public String getPageTitle() {
        return "Login";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "security/login";
    }
}
