package com.yahya.growth.stockmanagementsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {

    @GetMapping("/")
    public String home() {
        return "redirect:/items";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "security/login";
    }
}
