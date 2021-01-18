package com.yahya.growth.stockmanagementsystem.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExceptionController implements ErrorController {

    @ModelAttribute("title")
    public String getPageTitle() {
        return "Sorry!";
    }
    
    @RequestMapping("/error")
    public String handleError(Model model) {
//        model.addAttribute("exception", exception);
        model.addAttribute("pageName", "error/error");
        return "common/header";
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
