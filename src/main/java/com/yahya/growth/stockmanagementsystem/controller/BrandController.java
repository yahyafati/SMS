package com.yahya.growth.stockmanagementsystem.controller;

import com.yahya.growth.stockmanagementsystem.model.Brand;
import com.yahya.growth.stockmanagementsystem.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("")
    public String brands(Model model) {
        model.addAttribute("brands", brandService.findAll());
        return "brand/all";
    }

    @GetMapping("/{brandId}")
    public String detail(@PathVariable int brandId, Model model) {
        model.addAttribute("brand", brandService.findById(brandId));
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

    @GetMapping("/{brandId}/edit")
    public String edit(@PathVariable int brandId, Model model) {
        model.addAttribute("brand", brandService.findById(brandId));
        model.addAttribute("action", "edit");
        return "brand/edit";
    }

    @PostMapping("/{brandId}/edit")
    public String editPOST(@PathVariable int brandId, @ModelAttribute Brand brand) {
        brand.setId(brandId);
        brandService.save(brand);
        return "redirect:/brand/" + brandId;
    }

    @GetMapping("/{brandId}/delete")
    public String delete(@PathVariable int brandId) {
        brandService.deleteById(brandId);
        return "redirect:/brand";
    }
}
