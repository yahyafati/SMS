package com.yahya.growth.stockmanagementsystem.service.implematation;

import com.yahya.growth.stockmanagementsystem.dao.CategoryDao;
import com.yahya.growth.stockmanagementsystem.model.Category;
import com.yahya.growth.stockmanagementsystem.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;


    @Override
    public Category findById(int categoryId) {
        return null;
    }

    @Override
    public int save(Category category) {
        return 0;
    }
}
