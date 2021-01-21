package com.yahya.growth.stockmanagementsystem.controller;

import com.yahya.growth.stockmanagementsystem.utilities.TransactionException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

public interface BasicControllerSkeleton<T> {

    @ModelAttribute("title")
    String getPageTitle();

    String index(Model model);

    String detail(int id, Model model);

    String addNewItem(Model model);

    String addNewPOST(T obj);

    String edit(int id, Model model);

    String editPost(int id, T obj);

    String delete(int id);
}
