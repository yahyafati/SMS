package com.yahya.growth.stockmanagementsystem.service.implematation;

import com.yahya.growth.stockmanagementsystem.dao.SubCategoryDao;
import com.yahya.growth.stockmanagementsystem.model.Subcategory;
import com.yahya.growth.stockmanagementsystem.service.BrandService;
import com.yahya.growth.stockmanagementsystem.service.CategoryService;
import com.yahya.growth.stockmanagementsystem.service.ItemService;
import com.yahya.growth.stockmanagementsystem.service.SubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubcategoryServiceImpl implements SubcategoryService {

    private final SubCategoryDao subCategoryDao;
    private final CategoryService categoryService;
    private final ItemService itemService;

    public SubcategoryServiceImpl(SubCategoryDao subCategoryDao, CategoryService categoryService, ItemService itemService) {
        this.subCategoryDao = subCategoryDao;
        this.categoryService = categoryService;
        this.itemService = itemService;
    }

    @Override
    public Subcategory findById(int subcategoryId) {
        return subCategoryDao.findById(subcategoryId).orElseThrow();
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
