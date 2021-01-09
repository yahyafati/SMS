package com.yahya.growth.stockmanagementsystem.controller;

import com.yahya.growth.stockmanagementsystem.model.Item;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

public interface BasicControllerSkeleton<T> {

    String index(Model model);

    @GetMapping("")
    String index(Model model, Principal principal);

    String detail(int id, Model model);

    String addNewItem(Model model);

    String addNewPOST(T obj);

    String edit(int id, Model model);

    String editPost(int id, T obj);

    String delete(int id);
}
