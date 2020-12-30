package com.yahya.growth.stockmanagementsystem.service.implematation;

import com.yahya.growth.stockmanagementsystem.dao.SupplierDao;
import com.yahya.growth.stockmanagementsystem.model.Supplier;
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
    public Supplier findById(int supplierId) {
        return supplierDao.findById(supplierId).orElseThrow();
    }

    @Override
    public Supplier save(Supplier supplier) {
        supplier.setItems(
                supplier.getItems()
                .stream()
                .map(item -> itemService.findById(item.getId()))
                .collect(Collectors.toSet())
        );
        supplier.setCategories(
                supplier.getCategories()
                .stream()
                .map(category -> categoryService.findById(category.getId()))
                .collect(Collectors.toSet())
        );
        return supplierDao.save(supplier);
    }

    @Override
    public List<Supplier> findAll() {
        return supplierDao.findAll();
    }

    @Override
    public void deleteById(Integer supplierId) {
        supplierDao.deleteById(supplierId);
    }
}
