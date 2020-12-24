package com.yahya.growth.stockmanagementsystem.service;

import com.yahya.growth.stockmanagementsystem.model.Brand;

import java.util.List;

public interface BrandService {

    Brand findById(int brandId);

    Brand save(Brand brand);

    List<Brand> findAll();

    void deleteById(Integer brandId);
}
