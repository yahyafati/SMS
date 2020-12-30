package com.yahya.growth.stockmanagementsystem.restController;

import com.yahya.growth.stockmanagementsystem.model.Category;
import com.yahya.growth.stockmanagementsystem.model.Supplier;
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
    public List<Supplier> getAllSuppliers(){
        return supplierService.findAll();
    }

    @GetMapping("/{supplierId}")
    public Supplier getSupplier(@PathVariable int supplierId) {
        return supplierService.findById(supplierId);
    }

    @PostMapping("")
    public Supplier addSupplier(@RequestBody Supplier supplier) {
        return supplierService.save(supplier);
    }

    @PutMapping("/{supplierId}")
    public Supplier updateSupplier(@RequestBody Supplier supplier, @PathVariable int supplierId) {
        supplier.setId(supplierId);
        return supplierService.save(supplier);
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
    public Supplier addCategory(@PathVariable int supplierId, @RequestBody Category category) {
        Supplier supplier = supplierService.findById(supplierId);
        supplier.getCategories().add(category);
        return supplierService.save(supplier);
    }

    @DeleteMapping("/{supplierId}/category/{categoryId}")
    public Supplier removeCategory(@PathVariable int supplierId, @PathVariable int categoryId) {
        Supplier supplier = supplierService.findById(supplierId);
        supplier.getCategories().removeIf(category -> category.getId() == categoryId);
        return supplierService.save(supplier);
    }

}
