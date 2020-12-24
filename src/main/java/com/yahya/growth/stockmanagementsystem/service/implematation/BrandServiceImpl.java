package com.yahya.growth.stockmanagementsystem.service.implematation;

import com.yahya.growth.stockmanagementsystem.dao.BrandDao;
import com.yahya.growth.stockmanagementsystem.model.Brand;
import com.yahya.growth.stockmanagementsystem.service.BrandService;
import com.yahya.growth.stockmanagementsystem.service.CategoryService;
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

    @Override
    public Brand findById(int brandId) {
        return brandDao.findById(brandId).orElseThrow();
    }

    @Override
    public Brand save(Brand brand) {
        brand.setCategorySet(
                brand.getCategorySet()
                .stream()
                .map(category -> categoryService.findById(category.getId()))
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
