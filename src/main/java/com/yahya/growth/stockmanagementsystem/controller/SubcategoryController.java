package com.yahya.growth.stockmanagementsystem.controller;

import com.yahya.growth.stockmanagementsystem.model.Subcategory;
import com.yahya.growth.stockmanagementsystem.service.CategoryService;
import com.yahya.growth.stockmanagementsystem.service.SubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/subcategory")
public class SubcategoryController {

    @Autowired
    private SubcategoryService subcategoryService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public String subcategories(Model model) {
        model.addAttribute("subcategories", subcategoryService.findAll());
        return "subcategory/all";
    }

    @GetMapping("/{subcategoryId}")
    public String detail(@PathVariable int subcategoryId, Model model) {
        model.addAttribute("subcategory", subcategoryService.findById(subcategoryId));
        return "subcategory/detail";
    }

    @GetMapping("/new")
    public String addSubcategory(Model model) {
        Subcategory subCategory = new Subcategory();
//        subCategory.setCategory(new Category());
        model.addAttribute("subcategory", subCategory);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("action", "new");
        return "subcategory/edit";
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
        return "subcategory/edit";
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
