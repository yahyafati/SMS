package com.yahya.growth.stockmanagementsystem.controller;

import com.yahya.growth.stockmanagementsystem.model.Item;
import com.yahya.growth.stockmanagementsystem.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("items", itemService.findAll());
        return "index";
    }

    @GetMapping("/{item_id}")
    public String detail(@PathVariable(name = "item_id") int itemId, Model model) {
        model.addAttribute("item", itemService.findById(itemId));
        return "item/detail";
    }

    @GetMapping("/new")
    public String addNewItem(Model model) {
        model.addAttribute("item", new Item());
        model.addAttribute("action", "new");
        return "item/edit";
    }

    @PostMapping("/new")
    public String addNewItemPOST(@ModelAttribute Item item) {
        item = itemService.save(item);
        return "redirect:/" + item.getId();
    }

    @GetMapping("/{item_id}/edit")
    public String edit(@PathVariable(name = "item_id") int itemId, Model model) {
        model.addAttribute("item", itemService.findById(itemId));
        model.addAttribute("action", "edit");
        return "item/edit";
    }

    @PostMapping("/{item_id}/edit")
    public String editPost(@PathVariable(name = "item_id") int itemId, @ModelAttribute Item item) {
        item.setId(itemId);
        itemService.save(item);
        return "redirect:/" + itemId;
    }

    @GetMapping("/{item_id}/delete")
    public String delete(@PathVariable(name = "item_id") int itemId) {
        itemService.deleteById(itemId);
        return "redirect:/";
    }

}
