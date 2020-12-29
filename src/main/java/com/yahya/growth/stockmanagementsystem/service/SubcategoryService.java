package com.yahya.growth.stockmanagementsystem.service;


import com.yahya.growth.stockmanagementsystem.model.Subcategory;

import java.util.List;

public interface SubcategoryService {

    Subcategory findById(int subcategoryId);

    Subcategory save(Subcategory subCategory);

    List<Subcategory> findAll();

    List<Subcategory> findAllByCategory(int categoryId);

    void deleteById(Integer subcategoryId);
}
