package com.yahya.growth.stockmanagementsystem.restController;

import com.yahya.growth.stockmanagementsystem.model.Category;
import com.yahya.growth.stockmanagementsystem.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryRestController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public List<Category> getAllItems() {
        return categoryService.findAll();
    }

    @GetMapping("/{category_id}")
    public Category getItem(@PathVariable(name = "category_id") Integer categoryId) {
        return categoryService.findById(categoryId);
    }

    @PostMapping("")
    public Category addItem(@RequestBody Category category) {
        return categoryService.save(category);
    }

    @PutMapping("/category/{category_id}")
    public Category updateItem(@RequestBody Category category, @PathVariable(name = "category_id") Integer categoryId) {
        category.setId(categoryId);
        return categoryService.save(category);
    }

    @DeleteMapping("/{category_id}")
    public String deleteItem(@PathVariable(name = "category_id") Integer categoryId) {
        categoryService.deleteById(categoryId);
        return "Category has been deleted";
    }
}
