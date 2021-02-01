package com.yahya.growth.stockmanagementsystem.service.implematation;

import com.yahya.growth.stockmanagementsystem.dao.SubCategoryDao;
import com.yahya.growth.stockmanagementsystem.model.Subcategory;
import com.yahya.growth.stockmanagementsystem.service.CategoryService;
import com.yahya.growth.stockmanagementsystem.service.SubcategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubcategoryServiceImpl implements SubcategoryService {

    private final SubCategoryDao subCategoryDao;
    private final CategoryService categoryService;

    public SubcategoryServiceImpl(SubCategoryDao subCategoryDao, CategoryService categoryService) {
        this.subCategoryDao = subCategoryDao;
        this.categoryService = categoryService;
    }

    @Override
    public Subcategory findById(int subcategoryId) {
        return subCategoryDao.findById(subcategoryId).orElseThrow(() -> {throw new IllegalArgumentException("The Item you are looking for is no longer available.");});
    }

    @Override
    public Subcategory save(Subcategory subCategory) {
        System.out.println(subCategory.getCategory());
//        subCategory.setItems(
//                subCategory.getItems()
//                .stream()
//                .map(item -> itemService.findById(item.getId()))
//                .collect(Collectors.toSet())
//        );
        return subCategoryDao.save(subCategory);
    }

    @Override
    public List<Subcategory> findAll() {
        return subCategoryDao.findAll();
    }

    @Override
    public List<Subcategory> findAllByCategory(int categoryId) {
        return subCategoryDao.findAllByCategory(categoryService.findById(categoryId));
    }

    @Override
    public void deleteById(Integer subcategoryId) {
        subCategoryDao.deleteById(subcategoryId);
    }
}
