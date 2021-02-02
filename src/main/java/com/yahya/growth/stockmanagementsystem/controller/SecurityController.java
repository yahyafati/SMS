package com.yahya.growth.stockmanagementsystem.controller;

import com.yahya.growth.stockmanagementsystem.model.User;
import com.yahya.growth.stockmanagementsystem.service.UserService;
import com.yahya.growth.stockmanagementsystem.utilities.ChangePassword;
import com.yahya.growth.stockmanagementsystem.utilities.FlashMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
public class SecurityController {

    private final UserService userService;

    @Autowired
    public SecurityController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("title")
    public String getPageTitle() {
        return "Login";
    }

    @GetMapping("/login")
    public String getLoginPage(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", error);
        }
        return "security/login";
    }

    @GetMapping("/changePassword")
    @PreAuthorize("isAuthenticated()")
    public String changePassword(@RequestParam(required = false) String error, Model model) {
        model.addAttribute("error", error);
        model.addAttribute("changePassword", new ChangePassword());
        model.addAttribute("title", "Change Password");
        model.addAttribute("pageName", "user/changePassword");
        return "common/header";
    }

    @PostMapping("/changePassword")
    @PreAuthorize("isAuthenticated()")
    public RedirectView changePasswordPOST(ChangePassword changePassword, Principal principal, RedirectAttributes redir) {
        RedirectView redirectView = new RedirectView("/dashboard");
        User user = userService.findByUsername(principal.getName());
        if (!userService.checkIfPasswordIsValid(user, changePassword.getCurrentPassword())) {
            redir.addAttribute("error", true);
            redirectView.setUrl("/users/changePassword");
            return redirectView;
        }
        FlashMessage flashMessage = new FlashMessage("Your password has been changed.", "", FlashMessage.Type.SUCCESS);
        redir.addFlashAttribute("dialogFlash", flashMessage);
        userService.changePassword(user, changePassword.getNewPassword());
        return redirectView;
    }

}
