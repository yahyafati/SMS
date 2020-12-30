package com.yahya.growth.stockmanagementsystem.service.implematation;

import com.yahya.growth.stockmanagementsystem.dao.BrandDao;
import com.yahya.growth.stockmanagementsystem.model.Brand;
import com.yahya.growth.stockmanagementsystem.model.Subcategory;
import com.yahya.growth.stockmanagementsystem.service.BrandService;
import com.yahya.growth.stockmanagementsystem.service.CategoryService;
import com.yahya.growth.stockmanagementsystem.service.SubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandDao brandDao;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SubcategoryService subcategoryService;

    @Override
    public Brand findById(int brandId) {
        return brandDao.findById(brandId).orElseThrow();
    }

    @Override
    public Brand save(Brand brand) {
        brand.setCategories(
                brand.getCategories()
                .stream()
                .map(category -> categoryService.findById(category.getId()))
                .collect(Collectors.toSet())
        );
        brand.setSubcategories(
                brand.getSubcategories()
                .stream()
                .map(subcategory -> subcategoryService.findById(subcategory.getId()))
                .collect(Collectors.toSet())
        );
        return brandDao.save(brand);
    }

    @Override
    public List<Brand> findAll() {
        return brandDao.findAll();
    }

    @Override
    public void deleteById(Integer brandId) {
        brandDao.deleteById(brandId);
    }
}
