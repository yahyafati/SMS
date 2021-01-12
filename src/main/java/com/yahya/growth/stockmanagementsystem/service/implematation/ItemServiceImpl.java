package com.yahya.growth.stockmanagementsystem.service.implematation;

import com.yahya.growth.stockmanagementsystem.dao.ItemDao;
import com.yahya.growth.stockmanagementsystem.model.Item;
import com.yahya.growth.stockmanagementsystem.service.BrandService;
import com.yahya.growth.stockmanagementsystem.service.CategoryService;
import com.yahya.growth.stockmanagementsystem.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemDao itemDao;
    private final CategoryService categoryService;
    private final BrandService brandService;

    @Autowired
    public ItemServiceImpl(ItemDao itemDao, CategoryService categoryService, BrandService brandService) {
        this.itemDao = itemDao;
        this.categoryService = categoryService;
        this.brandService = brandService;
    }

    @Override
    public Item findById(int itemId) {
        return itemDao.findById(itemId).orElseThrow();
    }

    @Override
    public Item save(Item item) {
        if (item.getCategory() != null)
            item.setCategory(categoryService.findById(item.getCategory().getId()));
//        if (item.getSubcategory() != null)
//            item.setSubcategory(subcategoryService.findById(item.getSubcategory().getId()));
        if (item.getBrand() != null)
            item.setBrand(brandService.findById(item.getBrand().getId()));
        return itemDao.save(item);
    }

    @Override
    public List<Item> findAll() {
        return itemDao.findAll();
    }

    /**
     * TODO It doesn't do what it says it does.
     * @return List of Available Items in Stock right now
     */
    @Override
    public List<Item> findAllAvailableItems() {
        return itemDao.findAll();
//        return itemDao.findAllByQuantityGreaterThan(0);
    }

    @Override
    public void deleteById(Integer itemId) {
        itemDao.deleteById(itemId);
    }

}
