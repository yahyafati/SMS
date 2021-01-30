package com.yahya.growth.stockmanagementsystem.controller;

import com.yahya.growth.stockmanagementsystem.model.Item;
import com.yahya.growth.stockmanagementsystem.model.TransactionType;
import com.yahya.growth.stockmanagementsystem.service.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;
    private final SubcategoryService subcategoryService;
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final ItemTransactionService itemTransactionService;

    public ItemController(ItemService itemService, SubcategoryService subcategoryService, CategoryService categoryService, BrandService brandService, ItemTransactionService itemTransactionService) {
        this.itemService = itemService;
        this.subcategoryService = subcategoryService;
        this.categoryService = categoryService;
        this.brandService = brandService;
        this.itemTransactionService = itemTransactionService;
    }

    @ModelAttribute("title")
    public String getPageTitle() {
        return "Products";
    }

    @GetMapping("")
    public String index(Model model) {
        List<Item> items = itemService.findAll();
        items.forEach(item -> item.setQuantity(itemTransactionService.getQuantityOfItem(item)));
        model.addAttribute("items", items);
//        return "item/all";
        model.addAttribute("pageName", "item/all");
        return "common/header";
    }

    @GetMapping("/{item_id}")
    public String detail(@PathVariable(name = "item_id") int itemId, Model model) {
        Item item = itemService.findById(itemId);
        item.setQuantity(itemTransactionService.getQuantityOfItem(item));
        model.addAttribute("item", item);
//        return "item/detail";
        model.addAttribute("pageName", "item/detail");
        return "common/header";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAuthority('item:write')")
    public String addNewItem(Model model) {
        model.addAttribute("item", new Item());
        model.addAttribute("action", "new");
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("subcategories", subcategoryService.findAll());
        model.addAttribute("brands", brandService.findAll());
//        model.addAttribute("suppliers", storeService.findAll());
//        return "item/edit";
        model.addAttribute("pageName", "item/edit");
        return "common/header";
    }

    @PostMapping("/new")
    @PreAuthorize("hasAuthority('item:write')")
    public String addNewItemPOST(@ModelAttribute Item item) {
        item = itemService.save(item);
        return "redirect:/items/" + item.getId();
    }

    @GetMapping("/edit")
    @PreAuthorize("hasAuthority('item:write')")
    public String edit(@RequestParam(name = "id") int itemId, Model model) {
        model.addAttribute("item", itemService.findById(itemId));
        model.addAttribute("action", "edit");
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("subcategories", subcategoryService.findAll());
        model.addAttribute("brands", brandService.findAll());
//        model.addAttribute("suppliers", storeService.findAll());
//        return "item/edit";
        model.addAttribute("pageName", "item/edit");
        return "common/header";
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('item:write')")
    public String editPost(@RequestParam(name = "id") int itemId, @ModelAttribute Item item) {
        item.setId(itemId);
        itemService.save(item);
        return "redirect:/items/" + itemId;
    }

    @GetMapping("/delete")
    @PreAuthorize("hasAuthority('item:write')")
    public String delete(@RequestParam(name = "id") int itemId) {
        itemService.deleteById(itemId);
        return "redirect:/items";
    }

}
