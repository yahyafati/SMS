package com.yahya.growth.stockmanagementsystem.service.implematation;

import com.yahya.growth.stockmanagementsystem.dao.CategoryDao;
import com.yahya.growth.stockmanagementsystem.model.Category;
import com.yahya.growth.stockmanagementsystem.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;


    @Override
    public Category findById(int categoryId) {
        return categoryDao.getOne(categoryId);
    }

    @Override
    public int save(Category category) {
        return categoryDao.save(category).getId();
    }

    @Override
    public List<Category> findAll() {
        return categoryDao.findAll();
    }
}
