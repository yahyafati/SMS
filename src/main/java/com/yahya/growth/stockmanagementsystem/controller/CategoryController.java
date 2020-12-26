package com.yahya.growth.stockmanagementsystem.controller;

import com.yahya.growth.stockmanagementsystem.model.Category;
import com.yahya.growth.stockmanagementsystem.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public String categories(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "category/all";
    }

    @GetMapping("/{category_id}")
    public String detail(@PathVariable(name = "category_id") int categoryId, Model model) {
        model.addAttribute("category", categoryService.findById(categoryId));
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

    @GetMapping("/{category_id}/edit")
    public String edit(@PathVariable(name = "category_id") int categoryId, Model model) {
        model.addAttribute("category", categoryService.findById(categoryId));
        model.addAttribute("action", "edit");
        return "category/edit";
    }

    @PostMapping("/{category_id}/edit")
    public String editPOST(@PathVariable(name = "category_id") int categoryId, @ModelAttribute Category category) {
        category.setId(categoryId);
        categoryService.save(category);
        return "redirect:/category/" + categoryId;
    }

    @GetMapping("/{category_id}/delete")
    public String delete(@PathVariable(name = "category_id") int categoryId) {
        categoryService.deleteById(categoryId);
        return "redirect:/category";
    }
}
