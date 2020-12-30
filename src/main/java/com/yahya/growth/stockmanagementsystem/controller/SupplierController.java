package com.yahya.growth.stockmanagementsystem.controller;

import com.yahya.growth.stockmanagementsystem.model.Category;
import com.yahya.growth.stockmanagementsystem.model.Supplier;
import com.yahya.growth.stockmanagementsystem.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping("")
    public String suppliers(Model model) {
        model.addAttribute("suppliers", supplierService.findAll());
        return "supplier/all";
    }

    @GetMapping("/{supplierId}")
    public String detail(@PathVariable int supplierId, Model model) {
        // FIXME Probable Error DON'T FORGET TO TEST THIS
        model.addAttribute("supplier", supplierService.findById(supplierId));
        return "supplier/detail";
    }

    @GetMapping("/new")
    public String addSupplier(Model model) {
        model.addAttribute("supplier", new Supplier());
        model.addAttribute("action", "new");
        return "supplier/edit";
    }

    @PostMapping("/new")
    public String addSupplierPOST(@ModelAttribute Supplier supplier) {
        supplier = supplierService.save(supplier);
        return "redirect:/supplier/" + supplier.getId();
    }

    @GetMapping("/edit")
    public String edit(@RequestParam(name = "id") int supplierId, Model model) {
        model.addAttribute("supplier", supplierService.findById(supplierId));
        model.addAttribute("action", "edit");
        return "supplier/edit";
    }

    @PostMapping("/edit")
    public String editPOST(@RequestParam(name = "id") int supplierId, @ModelAttribute Supplier supplier) {
        supplier.setId(supplierId);
        supplierService.save(supplier);
        return "redirect:/supplier/" + supplierId;
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(name = "id") int supplierId) {
        supplierService.deleteById(supplierId);
        return "redirect:/supplier";
    }
}
