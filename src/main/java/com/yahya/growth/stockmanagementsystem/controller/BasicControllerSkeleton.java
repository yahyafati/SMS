package com.yahya.growth.stockmanagementsystem.controller;

import org.springframework.ui.Model;

public interface BasicControllerSkeleton<T> {

    String index(Model model);

    String detail(int id, Model model);

    String addNewItem(Model model);

    String addNewPOST(T obj);

    String edit(int id, Model model);

    String editPost(int id, T obj);

    String delete(int id);
}
