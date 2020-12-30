package com.yahya.growth.stockmanagementsystem.controller;

import com.yahya.growth.stockmanagementsystem.model.Brand;
import com.yahya.growth.stockmanagementsystem.model.Category;
import com.yahya.growth.stockmanagementsystem.model.Subcategory;
import com.yahya.growth.stockmanagementsystem.service.BrandService;
import com.yahya.growth.stockmanagementsystem.service.CategoryService;
import com.yahya.growth.stockmanagementsystem.service.SubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SubcategoryService subcategoryService;

    @GetMapping("")
    public String brands(Model model) {
        model.addAttribute("brands", brandService.findAll());
        return "brand/all";
    }

    @GetMapping("/{brandId}")
    public String detail(@PathVariable int brandId, Model model) {
        model.addAttribute("brand", brandService.findById(brandId));
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("subcategories", subcategoryService.findAll());
        model.addAttribute("category", new Category());
        model.addAttribute("subcategory", new Subcategory());
        return "brand/detail";
    }

    @GetMapping("/new")
    public String addBrand(Model model) {
        model.addAttribute("brand", new Brand());
        model.addAttribute("action", "new");
        return "brand/edit";
    }

    @PostMapping("/new")
    public String addBrandPOST(@ModelAttribute Brand brand) {
        brand = brandService.save(brand);
        return "redirect:/brand/" + brand.getId();
    }

    @PostMapping("/addCategory")
    public String addCategoryPOST(@RequestParam(name = "id") int brandId, @ModelAttribute Category category) {
        Brand brand = brandService.findById(brandId);
        brand.getCategorySet().add(category);
        brandService.save(brand);
        return "redirect:/brand/" + brandId;
    }

    @GetMapping("/removeCategory")
    public String removeCategory(@RequestParam(name = "category") int categoryId, @RequestParam(name = "brand") int brandId) {
        Brand brand = brandService.findById(brandId);
        brand.getCategorySet().removeIf(category -> category.getId() == categoryId);
        brandService.save(brand);
        return "redirect:/brand/" + brandId;
    }

    @GetMapping("/removeSubcategory")
    public String removeSubcategory(@RequestParam(name = "subcategory") int subcategoryId, @RequestParam(name = "brand") int brandId) {
        Brand brand = brandService.findById(brandId);
        brand.getSubcategorySet().removeIf(subcategory -> subcategory.getId() == subcategoryId);
        brandService.save(brand);
        return "redirect:/brand/" + brandId;
    }

    @PostMapping("/addSubcategory")
    public String addSubcategoryPOST(@RequestParam(name = "id") int brandId, @ModelAttribute Subcategory subCategory) {
        Brand brand = brandService.findById(brandId);
        brand.getSubcategorySet().add(subCategory);
        brandService.save(brand);
        return "redirect:/brand/" + brandId;
    }

    @GetMapping("/edit")
    public String edit(@RequestParam(name = "id") int brandId, Model model) {
        model.addAttribute("brand", brandService.findById(brandId));
        model.addAttribute("action", "edit");
        return "brand/edit";
    }

    @PostMapping("/edit")
    public String editPOST(@RequestParam(name = "id") int brandId, @ModelAttribute Brand brand) {
        brand.setId(brandId);
        brandService.save(brand);
        return "redirect:/brand/" + brandId;
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(name = "id") int brandId) {
        brandService.deleteById(brandId);
        return "redirect:/brand";
    }
}
