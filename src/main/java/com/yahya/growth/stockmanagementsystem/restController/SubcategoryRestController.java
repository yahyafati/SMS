package com.yahya.growth.stockmanagementsystem.restController;

import com.yahya.growth.stockmanagementsystem.model.SubCategory;
import com.yahya.growth.stockmanagementsystem.service.SubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SubcategoryRestController {

    @Autowired
    private SubcategoryService subcategoryService;

    @GetMapping("/")
    public List<SubCategory> getAllItems() {
        return subcategoryService.findAll();
    }

    @GetMapping("/{subcategory_id}")
    public SubCategory getItem(@PathVariable(name = "subcategory_id") Integer subcategoryId) {
        return subcategoryService.findById(subcategoryId);
    }

    @PostMapping("/")
    public SubCategory addItem(@RequestBody SubCategory subCategory) {
        return subcategoryService.save(subCategory);
    }

    @PutMapping("/{subcategory_id}")
    public SubCategory updateItem(@RequestBody SubCategory subCategory, @PathVariable(name = "subcategory_id") Integer subcategoryId) {
        subCategory.setId(subcategoryId);
        return subcategoryService.save(subCategory);
    }

    @DeleteMapping("/{subcategory_id}")
    public String deleteItem(@PathVariable(name = "subcategory_id") Integer subcategoryId) {
        subcategoryService.deleteById(subcategoryId);
        return "Subcategory has been deleted";
    }

}
