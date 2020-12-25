package com.yahya.growth.stockmanagementsystem.service.implematation;

import com.yahya.growth.stockmanagementsystem.dao.CategoryDao;
import com.yahya.growth.stockmanagementsystem.dao.ItemDao;
import com.yahya.growth.stockmanagementsystem.model.Item;
import com.yahya.growth.stockmanagementsystem.service.BrandService;
import com.yahya.growth.stockmanagementsystem.service.CategoryService;
import com.yahya.growth.stockmanagementsystem.service.ItemService;
import com.yahya.growth.stockmanagementsystem.service.SubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemDao itemDao;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SubcategoryService subcategoryService;
    @Autowired
    private BrandService brandService;

    @Override
    public Item findById(int itemId) {
        return itemDao.findById(itemId).orElseThrow();
    }

    @Override
    public Item save(Item item) {
        item.setCategoryList(
                item.getCategoryList()
                        .stream()
                        .map(category -> categoryService.findById(category.getId()))
                        .collect(Collectors.toSet())
        );
        if (item.getSubCategory() != null)
            item.setSubCategory(subcategoryService.findById(item.getSubCategory().getId()));
        if (item.getBrand() != null)
            item.setBrand(brandService.findById(item.getBrand().getId()));
        return itemDao.save(item);
    }

    @Override
    public List<Item> findAll() {
        return itemDao.findAll();
    }

    @Override
    public void deleteById(Integer itemId) {
        itemDao.deleteById(itemId);
    }

}
