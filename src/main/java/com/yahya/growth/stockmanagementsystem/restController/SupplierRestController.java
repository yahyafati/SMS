package com.yahya.growth.stockmanagementsystem.restController;

import com.yahya.growth.stockmanagementsystem.model.Category;
import com.yahya.growth.stockmanagementsystem.model.Store;
import com.yahya.growth.stockmanagementsystem.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/supplier")
public class SupplierRestController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping("")
    public List<Store> getAllSuppliers(){
        return supplierService.findAll();
    }

    @GetMapping("/{supplierId}")
    public Store getSupplier(@PathVariable int supplierId) {
        return supplierService.findById(supplierId);
    }

    @PostMapping("")
    public Store addSupplier(@RequestBody Store store) {
        return supplierService.save(store);
    }

    @PutMapping("/{supplierId}")
    public Store updateSupplier(@RequestBody Store store, @PathVariable int supplierId) {
        store.setId(supplierId);
        return supplierService.save(store);
    }

    @DeleteMapping("/{supplierId}")
    public String deleteSupplier(@PathVariable int supplierId) {
        supplierService.deleteById(supplierId);
        return "Supplier has been removed";
    }

    /*
     * Category Manipulations
     */

    @GetMapping("/{supplierId}/category")
    public Set<Category> getCategories(@PathVariable int supplierId) {
        return supplierService.findById(supplierId).getCategories();
    }

    @PostMapping("/{supplierId}/category")
    public Store addCategory(@PathVariable int supplierId, @RequestBody Category category) {
        Store store = supplierService.findById(supplierId);
        store.getCategories().add(category);
        return supplierService.save(store);
    }

    @DeleteMapping("/{supplierId}/category/{categoryId}")
    public Store removeCategory(@PathVariable int supplierId, @PathVariable int categoryId) {
        Store store = supplierService.findById(supplierId);
        store.getCategories().removeIf(category -> category.getId() == categoryId);
        return supplierService.save(store);
    }

}
