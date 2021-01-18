package com.yahya.growth.stockmanagementsystem.controller;

import com.yahya.growth.stockmanagementsystem.model.UserProfile;
import com.yahya.growth.stockmanagementsystem.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @Autowired
    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @ModelAttribute("title")
    public String getPageTitle() {
        return "Profile";
    }

    @GetMapping("")
    public String index(Model model, Principal principal) {
        UserProfile userProfile = userProfileService.findByUsername(principal.getName());
        model.addAttribute("profile", userProfile);
//        return "profile/profile";
        model.addAttribute("pageName", "profile/profile");
        return "common/header";
    }


}
