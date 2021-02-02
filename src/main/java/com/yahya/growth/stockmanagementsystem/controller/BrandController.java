package com.yahya.growth.stockmanagementsystem.controller;

import com.google.common.collect.Lists;
import com.yahya.growth.stockmanagementsystem.model.Brand;
import com.yahya.growth.stockmanagementsystem.model.Category;
import com.yahya.growth.stockmanagementsystem.model.Subcategory;
import com.yahya.growth.stockmanagementsystem.service.BrandService;
import com.yahya.growth.stockmanagementsystem.service.CategoryService;
import com.yahya.growth.stockmanagementsystem.service.SubcategoryService;
import com.yahya.growth.stockmanagementsystem.utilities.FlashMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

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
    public RedirectView delete(@RequestParam(name = "id") int brandId, RedirectAttributes redir) {
        RedirectView redirectView = new RedirectView("/brand");
        try {
            brandService.deleteById(brandId);
        } catch (DataIntegrityViolationException e) {
            FlashMessage flashMessage = new FlashMessage("This Brand can not be deleted as there are one or more items present under it.",
                    "", FlashMessage.Type.ERROR);
            redir.addFlashAttribute("dialogFlash", flashMessage);
        }
        return redirectView;
    }
}
