package com.yahya.growth.stockmanagementsystem.service.implematation;

import com.yahya.growth.stockmanagementsystem.dao.SubCategoryDao;
import com.yahya.growth.stockmanagementsystem.model.SubCategory;
import com.yahya.growth.stockmanagementsystem.service.CategoryService;
import com.yahya.growth.stockmanagementsystem.service.SubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubcategoryServiceImpl implements SubcategoryService {

    @Autowired
    private SubCategoryDao subCategoryDao;
    @Autowired
    private CategoryService categoryService;

    @Override
    public SubCategory findById(int subcategoryId) {
        return subCategoryDao.findById(subcategoryId).orElseThrow();
    }

    @Override
    public SubCategory save(SubCategory subCategory) {
        System.out.println(subCategory.getCategory());
//        subCategory.setCategory(categoryService.findById(subCategory.getCategory().getId()));
        return subCategoryDao.save(subCategory);
    }

    @Override
    public List<SubCategory> findAll() {
        return subCategoryDao.findAll();
    }

    @Override
    public void deleteById(Integer subcategoryId) {
        subCategoryDao.deleteById(subcategoryId);
    }
}
