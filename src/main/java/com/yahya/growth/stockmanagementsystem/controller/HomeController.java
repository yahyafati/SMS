package com.yahya.growth.stockmanagementsystem.controller;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("")
public class HomeController {

    @ModelAttribute("title")
    public String getPageTitle() {
        return "Dashboard";
    }

    @ModelAttribute("active")
    public List<String> getCurrentlyActive() {
        return Lists.newArrayList("dashboard");
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
