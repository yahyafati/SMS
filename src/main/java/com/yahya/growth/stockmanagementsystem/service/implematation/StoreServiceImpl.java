package com.yahya.growth.stockmanagementsystem.service.implematation;

import com.yahya.growth.stockmanagementsystem.dao.StoreDao;
import com.yahya.growth.stockmanagementsystem.model.Store;
import com.yahya.growth.stockmanagementsystem.service.CategoryService;
import com.yahya.growth.stockmanagementsystem.service.ItemService;
import com.yahya.growth.stockmanagementsystem.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoreServiceImpl implements StoreService {

    private final StoreDao storeDao;
    private final CategoryService categoryService;
    private final ItemService itemService;

    public StoreServiceImpl(StoreDao storeDao, CategoryService categoryService, ItemService itemService) {
        this.storeDao = storeDao;
        this.categoryService = categoryService;
        this.itemService = itemService;
    }

    @Override
    public Store findById(int storeId) {
        return storeDao.findById(storeId).orElseThrow();
    }

    @Override
    public Store save(Store store) {
        store.setItems(
                store.getItems()
                .stream()
                .map(item -> itemService.findById(item.getId()))
                .collect(Collectors.toSet())
        );
        store.setCategories(
                store.getCategories()
                .stream()
                .map(category -> categoryService.findById(category.getId()))
                .collect(Collectors.toSet())
        );
        return storeDao.save(store);
    }

    @Override
    public List<Store> findAll() {
        return storeDao.findAll();
    }

    @Override
    public void deleteById(Integer supplierId) {
        storeDao.deleteById(supplierId);
    }
}
