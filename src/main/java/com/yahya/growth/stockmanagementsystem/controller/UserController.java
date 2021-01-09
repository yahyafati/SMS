package com.yahya.growth.stockmanagementsystem.controller;

import com.yahya.growth.stockmanagementsystem.model.security.User;
import com.yahya.growth.stockmanagementsystem.service.AuthorityService;
import com.yahya.growth.stockmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/users")
public class UserController implements BasicControllerSkeleton<User>{

    private final UserService userService;
    private final AuthorityService authorityService;

    @Autowired
    public UserController(UserService userService, AuthorityService authorityService) {
        this.userService = userService;
        this.authorityService = authorityService;
    }

    @Override
    public String index(Model model) {
        return null;
    }

    @Override
    @GetMapping("")
    public String index(Model model, Principal principal) {
        model.addAttribute("users", userService.findAll());
        return "user/all";
    }

    @Override
    @GetMapping("/{id}")
    public String detail(@PathVariable int id, Model model) {
        model.addAttribute("user", userService.findById(id));
        var k = authorityService.findAll();
        System.out.println(k);
        model.addAttribute("allAuthorities", k);
        return "user/detail";
    }

    @Override
    @GetMapping("/new")
    public String addNewItem(Model model) {
        model.addAttribute("user", new User());
        return "user/signup";
    }

    @Override
    @PostMapping("/new")
    public String addNewPOST(User user) {
        user = userService.save(user);
        return "redirect:/users/" + user.getId();
    }

    @Override
    public String edit(int id, Model model) {
        throw new UnsupportedOperationException("Users can not be edited by other users.");
    }

    @Override
    public String editPost(int id, User obj) {
        throw new UnsupportedOperationException("Users can not be edited by other users.");
    }

//    @Override
//    @GetMapping("/edit")
//    public String edit(@RequestParam int id, Model model) {
//        model.addAttribute("user", userService.findById(id));
//        return "user/edit";
//    }
//
//    @Override
//    @PostMapping("/edit")
//    public String editPost(@RequestParam int id, User user) {
//        user.setId(id);
//        userService.save(user);
//        return "redirect:/users/" + user.getId();
//    }

    @Override
    @GetMapping("/delete")
    public String delete(@RequestParam int id) {
        userService.deleteById(id);
        return "redirect:/users";
    }
}
