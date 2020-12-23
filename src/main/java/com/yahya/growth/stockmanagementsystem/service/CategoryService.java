package com.yahya.growth.stockmanagementsystem.service;

import com.yahya.growth.stockmanagementsystem.model.Category;
import com.yahya.growth.stockmanagementsystem.model.Item;

import java.util.List;

public interface CategoryService {

    Category findById(int categoryId);

    int save(Category category);

    List<Category> findAll();
}
