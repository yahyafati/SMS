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

    @GetMapping("/removeSubcategory")
    public String removeSubcategory(@RequestParam int subcategoryId, @RequestParam int categoryId) {
        subcategoryService.deleteById(subcategoryId);
        return "redirect:/category/" + categoryId;
    }

    @GetMapping("/edit")
    public String edit(@RequestParam(name = "id") int categoryId, Model model) {
        model.addAttribute("category", categoryService.findById(categoryId));
        model.addAttribute("action", "edit");
        return "category/edit";
    }

    @PostMapping("/edit")
    public String editPOST(@RequestParam(name = "id") int categoryId, @ModelAttribute Category category) {
        category.setId(categoryId);
        categoryService.save(category);
        return "redirect:/category/" + categoryId;
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(name = "id") int categoryId) {
        // FIXME Cannot delete or update a parent row: a foreign key constraint fails (`stockms`.`item`, ...)
        categoryService.deleteById(categoryId);
        return "redirect:/category";
    }
}
