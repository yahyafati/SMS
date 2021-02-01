package com.yahya.growth.stockmanagementsystem.controller;

import com.google.common.collect.Lists;
import com.yahya.growth.stockmanagementsystem.model.UserProfile;
import com.yahya.growth.stockmanagementsystem.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

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

    @ModelAttribute("active")
    public List<String> getCurrentlyActive() {
        return Lists.newArrayList("userProfile");
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
