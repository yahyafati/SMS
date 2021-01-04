package com.yahya.growth.stockmanagementsystem.restController;

import com.yahya.growth.stockmanagementsystem.model.Category;
import com.yahya.growth.stockmanagementsystem.model.Store;
import com.yahya.growth.stockmanagementsystem.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/supplier")
public class StoreRestController {

    @Autowired
    private StoreService storeService;

    @GetMapping("")
    public List<Store> getAllSuppliers(){
        return storeService.findAll();
    }

    @GetMapping("/{supplierId}")
    public Store getSupplier(@PathVariable int supplierId) {
        return storeService.findById(supplierId);
    }

    @PostMapping("")
    public Store addSupplier(@RequestBody Store store) {
        return storeService.save(store);
    }

    @PutMapping("/{supplierId}")
    public Store updateSupplier(@RequestBody Store store, @PathVariable int supplierId) {
        store.setId(supplierId);
        return storeService.save(store);
    }

    @DeleteMapping("/{supplierId}")
    public String deleteSupplier(@PathVariable int supplierId) {
        storeService.deleteById(supplierId);
        return "Supplier has been removed";
    }

    /*
     * Category Manipulations
     */

    @GetMapping("/{supplierId}/category")
    public Set<Category> getCategories(@PathVariable int supplierId) {
        return storeService.findById(supplierId).getCategories();
    }

    @PostMapping("/{supplierId}/category")
    public Store addCategory(@PathVariable int supplierId, @RequestBody Category category) {
        Store store = storeService.findById(supplierId);
        store.getCategories().add(category);
        return storeService.save(store);
    }

    @DeleteMapping("/{supplierId}/category/{categoryId}")
    public Store removeCategory(@PathVariable int supplierId, @PathVariable int categoryId) {
        Store store = storeService.findById(supplierId);
        store.getCategories().removeIf(category -> category.getId() == categoryId);
        return storeService.save(store);
    }

}
