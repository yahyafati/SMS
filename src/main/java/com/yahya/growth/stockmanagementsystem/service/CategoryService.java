package com.yahya.growth.stockmanagementsystem.service;

import com.yahya.growth.stockmanagementsystem.model.Category;
import com.yahya.growth.stockmanagementsystem.model.Item;

public interface CategoryService {

    Category findById(int categoryId);

    int save(Category category);
}
