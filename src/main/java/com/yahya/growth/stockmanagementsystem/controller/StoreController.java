package com.yahya.growth.stockmanagementsystem.controller;

import com.yahya.growth.stockmanagementsystem.model.Store;
import com.yahya.growth.stockmanagementsystem.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @GetMapping("")
    public String stores(Model model) {
        model.addAttribute("stores", storeService.findAll());
        return "store/all";
    }

    @GetMapping("/{storeId}")
    public String detail(@PathVariable int storeId, Model model) {
        model.addAttribute("store", storeService.findById(storeId));
        return "store/detail";
    }

    @GetMapping("/new")
    public String addStore(Model model) {
        model.addAttribute("store", new Store());
        model.addAttribute("action", "new");
        return "store/edit";
    }

    @PostMapping("/new")
    public String addStorePOST(@ModelAttribute Store store) {
        store = storeService.save(store);
        return "redirect:/store/" + store.getId();
    }

    @GetMapping("/edit")
    public String edit(@RequestParam(name = "id") int storeId, Model model) {
        model.addAttribute("store", storeService.findById(storeId));
        model.addAttribute("action", "edit");
        return "store/edit";
    }

    @PostMapping("/edit")
    public String editPOST(@RequestParam(name = "id") int storeId, @ModelAttribute Store store) {
        store.setId(storeId);
        storeService.save(store);
        return "redirect:/store/" + storeId;
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(name = "id") int storeId) {
        storeService.deleteById(storeId);
        return "redirect:/store";
    }
}
