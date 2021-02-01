package com.yahya.growth.stockmanagementsystem.controller;

import com.google.common.collect.Lists;
import com.yahya.growth.stockmanagementsystem.model.Brand;
import com.yahya.growth.stockmanagementsystem.model.Category;
import com.yahya.growth.stockmanagementsystem.model.Subcategory;
import com.yahya.growth.stockmanagementsystem.service.BrandService;
import com.yahya.growth.stockmanagementsystem.service.CategoryService;
import com.yahya.growth.stockmanagementsystem.service.SubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/brand")
public class BrandController {

    private final BrandService brandService;
    private final CategoryService categoryService;
    private final SubcategoryService subcategoryService;

    @Autowired
    public BrandController(BrandService brandService, CategoryService categoryService, SubcategoryService subcategoryService) {
        this.brandService = brandService;
        this.categoryService = categoryService;
        this.subcategoryService = subcategoryService;
    }

    @ModelAttribute("title")
    public String getPageTitle() {
        return "Brands";
    }

    @ModelAttribute("active")
    public List<String> getCurrentlyActive() {
        return Lists.newArrayList("brand");
    }

    @GetMapping("")
    public String brands(Model model) {
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("pageName", "brand/all");
        return "common/header";
    }

    @GetMapping("/{brandId}")
    public String detail(@PathVariable int brandId, Model model) {
        Brand brand = brandService.findById(brandId);
        model.addAttribute("brand", brand);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("subcategories", subcategoryService.findAll());
        model.addAttribute("category", new Category());
        model.addAttribute("subcategory", new Subcategory());
//        return "brand/detail";
        model.addAttribute("pageName", "brand/detail");
        return "common/header";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAuthority('brand:write')")
    public String addBrand(Model model) {
        model.addAttribute("brand", new Brand());
        model.addAttribute("action", "new");
//        return "brand/edit";
        model.addAttribute("pageName", "brand/edit");
        return "common/header";
    }

    @PostMapping("/new")
    @PreAuthorize("hasAuthority('brand:write')")
    public String addBrandPOST(@ModelAttribute Brand brand) {
        brand = brandService.save(brand);
        return "redirect:/brand/" + brand.getId();
    }

    /* Many To Many Mapping */
//    @PostMapping("/addCategory")
//    public String addCategoryPOST(@RequestParam int brandId, @ModelAttribute Category category) {
//        Brand brand = brandService.findById(brandId);
//        brand.getCategories().add(category);
//        brandService.save(brand);
//        return "redirect:/brand/" + brandId;
//    }
//
//    @GetMapping("/removeCategory")
//    public String removeCategory(@RequestParam(name = "category") int categoryId, @RequestParam(name = "brand") int brandId) {
//        Brand brand = brandService.findById(brandId);
//        brand.getCategories().removeIf(category -> category.getId() == categoryId);
//        brandService.save(brand);
//        return "redirect:/brand/" + brandId;
//    }
//
//    @GetMapping("/removeSubcategory")
//    public String removeSubcategory(@RequestParam(name = "subcategory") int subcategoryId, @RequestParam(name = "brand") int brandId) {
//        Brand brand = brandService.findById(brandId);
//        brand.getSubcategories().removeIf(subcategory -> subcategory.getId() == subcategoryId);
//        brandService.save(brand);
//        return "redirect:/brand/" + brandId;
//    }
//
//    @PostMapping("/addSubcategory")
//    public String addSubcategoryPOST(@RequestParam int brandId, @ModelAttribute Subcategory subcategory) {
//        Brand brand = brandService.findById(brandId);
//        brand.getSubcategories().add(subcategory);
//        brandService.save(brand);
//        return "redirect:/brand/" + brandId;
//    }

    @GetMapping("/edit")
    @PreAuthorize("hasAuthority('brand:write')")
    public String edit(@RequestParam(name = "id") int brandId, Model model) {
        Brand brand = brandService.findById(brandId);
        model.addAttribute("brand", brand);
        model.addAttribute("action", "edit");
//        return "brand/edit";
        model.addAttribute("pageName", "brand/edit");
        return "common/header";
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('brand:write')")
    public String editPOST(@RequestParam(name = "id") int brandId, @ModelAttribute Brand brand) {
        brand.setId(brandId);
        brandService.save(brand);
        return "redirect:/brand/" + brandId;
    }

    @GetMapping("/delete")
    @PreAuthorize("hasAuthority('brand:write')")
    public String delete(@RequestParam(name = "id") int brandId) {
        brandService.deleteById(brandId);
        return "redirect:/brand";
    }
}
