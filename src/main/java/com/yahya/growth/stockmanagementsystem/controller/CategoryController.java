package com.yahya.growth.stockmanagementsystem.controller;

import com.google.common.collect.Lists;
import com.yahya.growth.stockmanagementsystem.model.Category;
import com.yahya.growth.stockmanagementsystem.model.Subcategory;
import com.yahya.growth.stockmanagementsystem.service.CategoryService;
import com.yahya.growth.stockmanagementsystem.service.SubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;
    private final SubcategoryService subcategoryService;

    @Autowired
    public CategoryController(CategoryService categoryService, SubcategoryService subcategoryService) {
        this.categoryService = categoryService;
        this.subcategoryService = subcategoryService;
    }

    @ModelAttribute("title")
    public String getPageTitle() {
        return "Category";
    }

    @ModelAttribute("active")
    public List<String> getCurrentlyActive() {
        return Lists.newArrayList("category");
    }

    @GetMapping("")
    public String categories(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("pageName", "category/all");
        return "common/header";
//        return "category/all";
    }

    @GetMapping("/{categoryId}")
    public String detail(@PathVariable int categoryId, Model model) {
        List<Subcategory> subCategories = subcategoryService.findAllByCategory(categoryId);
        model.addAttribute("category", categoryService.findById(categoryId));
        model.addAttribute("subcategories", subCategories);
//        return "category/detail";
        model.addAttribute("pageName", "category/detail");
        return "common/header";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAuthority('category:write')")
    public String addCategory(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("action", "new");
//        return "category/edit";
        model.addAttribute("pageName", "category/edit");
        return "common/header";
    }

    @PostMapping("/new")
    @PreAuthorize("hasAuthority('category:write')")
    public String addCategoryPOST(@ModelAttribute Category category) {
        category = categoryService.save(category);
        return "redirect:/category/" + category.getId();
    }

    @GetMapping("/removeSubcategory")
    @PreAuthorize("hasAuthority('category:write')")
    public String removeSubcategory(@RequestParam int subcategoryId, @RequestParam int categoryId) {
        subcategoryService.deleteById(subcategoryId);
        return "redirect:/category/" + categoryId;
    }

    @GetMapping("/edit")
    @PreAuthorize("hasAuthority('category:write')")
    public String edit(@RequestParam(name = "id") int categoryId, Model model) {
        model.addAttribute("category", categoryService.findById(categoryId));
        model.addAttribute("action", "edit");
//        return "category/edit";
        model.addAttribute("pageName", "category/edit");
        return "common/header";
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('category:write')")
    public String editPOST(@RequestParam(name = "id") int categoryId, @ModelAttribute Category category) {
        category.setId(categoryId);
        categoryService.save(category);
        return "redirect:/category/" + categoryId;
    }

    @GetMapping("/delete")
    @PreAuthorize("hasAuthority('category:write')")
    public String delete(@RequestParam(name = "id") int categoryId) {
        // FIXED Cannot delete or update a parent row: a foreign key constraint fails (`stockms`.`item`, ...)
        categoryService.deleteById(categoryId);
        return "redirect:/category";
    }
}
