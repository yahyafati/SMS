package com.yahya.growth.stockmanagementsystem.service;


import com.yahya.growth.stockmanagementsystem.model.SubCategory;

import java.util.List;

public interface SubcategoryService {

    SubCategory findById(int subcategoryId);

    int save(SubCategory subCategory);

    List<SubCategory> findAll();
}
