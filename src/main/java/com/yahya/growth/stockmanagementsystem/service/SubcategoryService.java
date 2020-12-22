package com.yahya.growth.stockmanagementsystem.service;


import com.yahya.growth.stockmanagementsystem.model.SubCategory;

public interface SubcategoryService {

    SubCategory findById(int subcategoryId);

    int save(SubCategory subCategory);
}
