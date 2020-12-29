package com.yahya.growth.stockmanagementsystem.controller;

import com.yahya.growth.stockmanagementsystem.model.Category;
import com.yahya.growth.stockmanagementsystem.model.Subcategory;
import com.yahya.growth.stockmanagementsystem.service.CategoryService;
import com.yahya.growth.stockmanagementsystem.service.SubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SubcategoryService subcategoryService;

    @GetMapping("")
    public String categories(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "category/all";
    }

    @GetMapping("/{categoryId}")
    public String detail(@PathVariable int categoryId, Model model) {
        List<Subcategory> subCategories = subcategoryService.findAllByCategory(categoryId);
        model.addAttribute("category", categoryService.findById(categoryId));
        model.addAttribute("subcategories", subCategories);
        return "category/detail";
    }

    @GetMapping("/new")
    public String addCategory(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("action", "new");
        return "category/edit";
    }

    @PostMapping("/new")
    public String addCategoryPOST(@ModelAttribute Category category) {
        category = categoryService.save(category);
        return "redirect:/category/" + category.getId();
    }

    @GetMapping("/{categoryId}/edit")
    public String edit(@PathVariable int categoryId, Model model) {
        model.addAttribute("category", categoryService.findById(categoryId));
        model.addAttribute("action", "edit");
        return "category/edit";
    }

    @PostMapping("/{categoryId}/edit")
    public String editPOST(@PathVariable int categoryId, @ModelAttribute Category category) {
        category.setId(categoryId);
        categoryService.save(category);
        return "redirect:/category/" + categoryId;
    }

    @GetMapping("/{categoryId}/delete")
    public String delete(@PathVariable int categoryId) {
        categoryService.deleteById(categoryId);
        return "redirect:/category";
    }
}
