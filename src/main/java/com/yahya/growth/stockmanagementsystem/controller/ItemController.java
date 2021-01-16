package com.yahya.growth.stockmanagementsystem.controller;

import com.yahya.growth.stockmanagementsystem.model.Item;
import com.yahya.growth.stockmanagementsystem.model.TransactionType;
import com.yahya.growth.stockmanagementsystem.service.*;
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
    private final StoreService storeService;
    private final ItemTransactionService itemTransactionService;

    public ItemController(ItemService itemService, SubcategoryService subcategoryService, CategoryService categoryService, BrandService brandService, StoreService storeService, ItemTransactionService itemTransactionService) {
        this.itemService = itemService;
        this.subcategoryService = subcategoryService;
        this.categoryService = categoryService;
        this.brandService = brandService;
        this.storeService = storeService;
        this.itemTransactionService = itemTransactionService;
    }

    // TODO Move this to a more appropriate class
//    /**
//     * This function takes in an item entity and returns the amount left in stock from the ItemTransaction table
//     * @param item an Item Entity
//     * @return amount left in stock
//     */
//    public int getQuantity(Item item) {
//        return itemTransactionService.findAllByItem(item).stream()
//                .mapToInt(itemTransaction -> {
//                            if (itemTransaction.getTransaction().getType() == TransactionType.PURCHASE) {
//                                return itemTransaction.getQuantity();
//                            } else {
//                                return -itemTransaction.getQuantity();
//                            }
//                        }
//                )
//                .sum();
//    }

    @GetMapping("")
    public String index(Model model) {
        List<Item> items = itemService.findAll();
        items.forEach(item -> item.setQuantity(itemTransactionService.getQuantityOfItem(item)));
        model.addAttribute("items", items);
        return "item/all";
    }

    @GetMapping("/{item_id}")
    public String detail(@PathVariable(name = "item_id") int itemId, Model model) {
        Item item = itemService.findById(itemId);
        item.setQuantity(itemTransactionService.getQuantityOfItem(item));
        model.addAttribute("item", item);
        return "item/detail";
    }

    @GetMapping("/new")
    public String addNewItem(Model model) {
        model.addAttribute("item", new Item());
        model.addAttribute("action", "new");
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("subcategories", subcategoryService.findAll());
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("suppliers", storeService.findAll());
        return "item/edit";
    }

    @PostMapping("/new")
    public String addNewItemPOST(@ModelAttribute Item item) {
        item = itemService.save(item);
        return "redirect:/items/" + item.getId();
    }

    @GetMapping("/edit")
    public String edit(@RequestParam(name = "id") int itemId, Model model) {
        model.addAttribute("item", itemService.findById(itemId));
        model.addAttribute("action", "edit");
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("subcategories", subcategoryService.findAll());
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("suppliers", storeService.findAll());
        return "item/edit";
    }

    @PostMapping("/edit")
    public String editPost(@RequestParam(name = "id") int itemId, @ModelAttribute Item item) {
        item.setId(itemId);
        itemService.save(item);
        return "redirect:/items/" + itemId;
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(name = "id") int itemId) {
        itemService.deleteById(itemId);
        return "redirect:/items";
    }

}
