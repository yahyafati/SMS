package com.yahya.growth.stockmanagementsystem.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

//@Controller
public class ExceptionController implements ErrorController {

    @ModelAttribute("title")
    public String getPageTitle() {
        return "Sorry!";
    }

//    @RequestMapping("/error")
//    @ExceptionHandler
    public String handleError(Model model, Exception exception) {
        model.addAttribute("pageName", "error/error");
        return "error/error";
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
