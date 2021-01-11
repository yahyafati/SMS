package com.yahya.growth.stockmanagementsystem.service.implematation;

import com.yahya.growth.stockmanagementsystem.dao.BrandDao;
import com.yahya.growth.stockmanagementsystem.model.Brand;
import com.yahya.growth.stockmanagementsystem.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandDao brandDao;

    @Autowired
    public BrandServiceImpl(BrandDao brandDao) {
        this.brandDao = brandDao;
    }

    @Override
    public Brand findById(int brandId) {
        return brandDao.findById(brandId).orElseThrow();
    }

    @Override
    public Brand save(Brand brand) {
//        brand.setCategories(
//                brand.getCategories()
//                .stream()
//                .map(category -> categoryService.findById(category.getId()))
//                .collect(Collectors.toSet())
//        );
//        brand.setSubcategories(
//                brand.getSubcategories()
//                .stream()
//                .map(subcategory -> subcategoryService.findById(subcategory.getId()))
//                .collect(Collectors.toSet())
//        );
        return brandDao.save(brand);
    }

    @Override
    public List<Brand> findAll() {
        return brandDao.findAll();
    }

    @Override
    @Transactional
    public void deleteById(Integer brandId) {
        brandDao.deleteById(brandId);
    }
}
