package com.yahya.growth.stockmanagementsystem.service.implematation;

import com.yahya.growth.stockmanagementsystem.dao.SupplierDao;
import com.yahya.growth.stockmanagementsystem.model.Store;
import com.yahya.growth.stockmanagementsystem.service.CategoryService;
import com.yahya.growth.stockmanagementsystem.service.ItemService;
import com.yahya.growth.stockmanagementsystem.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierDao supplierDao;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ItemService itemService;

    @Override
    public Store findById(int supplierId) {
        return supplierDao.findById(supplierId).orElseThrow();
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
        return supplierDao.save(store);
    }

    @Override
    public List<Store> findAll() {
        return supplierDao.findAll();
    }

    @Override
    public void deleteById(Integer supplierId) {
        supplierDao.deleteById(supplierId);
    }
}
