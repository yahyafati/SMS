package com.yahya.growth.stockmanagementsystem.controller;

import com.google.common.collect.Lists;
import com.yahya.growth.stockmanagementsystem.model.Item;
import com.yahya.growth.stockmanagementsystem.model.TransactionType;
import com.yahya.growth.stockmanagementsystem.service.*;
import com.yahya.growth.stockmanagementsystem.utilities.FlashMessage;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

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

    @ModelAttribute("active")
    public List<String> getCurrentlyActive() {
        return Lists.newArrayList("product");
    }

    @GetMapping("")
    public String index(Model model) {
        List<Item> items = itemService.findAll();
        items.forEach(item -> item.setQuantity(itemTransactionService.getQuantityOfItem(item)));
        model.addAttribute("items", items);
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
//        model.addAttribute("categories", categoryService.findAll());
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
        model.addAttribute("subcategories", subcategoryService.findAll());
        model.addAttribute("brands", brandService.findAll());
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

    @GetMapping("/toggleStatus")
    @PreAuthorize("hasAuthority('item:write')")
    public RedirectView toggleStatus(@RequestParam int id, @RequestParam String redirectTo) {
        RedirectView redirectView = new RedirectView(redirectTo);
        itemService.toggleStatus(id);
        return redirectView;
    }

    @GetMapping("/delete")
    @PreAuthorize("hasAuthority('item:write')")
    public RedirectView delete(@RequestParam int id, RedirectAttributes redir) {
        RedirectView redirectView = new RedirectView("/items", true);
        try {
            itemService.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            Item item = itemService.findById(id);
            if (item.isActive()) {
                FlashMessage flashMessage = new FlashMessage("This Item can not be deleted. Do you want to deactivate it instead?",
                        String.format("/items/toggleStatus?id=%d&redirectURL=/items", id), FlashMessage.Type.CONFIRM);
                redir.addFlashAttribute("dialogFlash", flashMessage);
            } else {
                FlashMessage flashMessage = new FlashMessage("This Item can not be deleted. However it is already deactivated.",
                        String.format("/items/toggleStatus?id=%d&redirectURL=/items", id), FlashMessage.Type.ERROR);
                redir.addFlashAttribute("dialogFlash", flashMessage);
            }

        }
        return redirectView;
    }

}
