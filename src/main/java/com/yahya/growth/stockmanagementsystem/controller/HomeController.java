package com.yahya.growth.stockmanagementsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class HomeController {

    @ModelAttribute("title")
    public String getPageTitle() {
        return "Dashboard";
    }

    @GetMapping("")
    public String home() {
        return "redirect:/dashboard";
    }
    @GetMapping("/dashboard")
    public String home(Model model) {
        model.addAttribute("pageName", "common/dashboard");
        return "common/header";
    }
}
