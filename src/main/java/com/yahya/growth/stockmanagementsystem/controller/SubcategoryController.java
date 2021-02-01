package com.yahya.growth.stockmanagementsystem.controller;

import com.google.common.collect.Lists;
import com.yahya.growth.stockmanagementsystem.model.Subcategory;
import com.yahya.growth.stockmanagementsystem.service.CategoryService;
import com.yahya.growth.stockmanagementsystem.service.SubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/subcategory")
public class SubcategoryController {

    private final SubcategoryService subcategoryService;
    private final CategoryService categoryService;

    @Autowired
    public SubcategoryController(SubcategoryService subcategoryService, CategoryService categoryService) {
        this.subcategoryService = subcategoryService;
        this.categoryService = categoryService;
    }

    @ModelAttribute("title")
    public String getPageTitle() {
        return "Category";
    }

    @ModelAttribute("active")
    public List<String> getCurrentlyActive() {
        return Lists.newArrayList("subcategory");
    }

    @GetMapping("")
    public String subcategories(Model model) {
        model.addAttribute("subcategories", subcategoryService.findAll());
//        return "subcategory/all";
        model.addAttribute("pageName", "subcategory/all");
        return "common/header";
    }

    @GetMapping("/{subcategoryId}")
    public String detail(@PathVariable int subcategoryId, Model model) {
        model.addAttribute("subcategory", subcategoryService.findById(subcategoryId));
//        return "subcategory/detail";
        model.addAttribute("pageName", "subcategory/detail");
        return "common/header";
    }

    @GetMapping("/new")
    public String addSubcategory(Model model) {
        Subcategory subCategory = new Subcategory();
//        subCategory.setCategory(new Category());
        model.addAttribute("subcategory", subCategory);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("action", "new");
//        return "subcategory/edit";
        model.addAttribute("pageName", "subcategory/edit");
        return "common/header";
    }

    @PostMapping("/new")
    public String addSubcategory(@ModelAttribute Subcategory subCategory) {
        subCategory = subcategoryService.save(subCategory);
        return "redirect:/subcategory/" + subCategory.getId();
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam(name = "id") int subcategoryId) {
        model.addAttribute("subcategory", subcategoryService.findById(subcategoryId));
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("action", "edit");
//        return "subcategory/edit";
        model.addAttribute("pageName", "subcategory/edit");
        return "common/header";
    }

    @PostMapping("/edit")
    public String editPOST(@RequestParam(name = "id") int subcategoryId, @ModelAttribute Subcategory subCategory) {
        subCategory.setId(subcategoryId);
        subcategoryService.save(subCategory);
        return "redirect:/subcategory/" + subcategoryId;
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(name = "id") int subcategoryId) {
        subcategoryService.deleteById(subcategoryId);
        return "redirect:/subcategory";
    }

}
