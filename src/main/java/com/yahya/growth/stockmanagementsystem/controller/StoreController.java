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

    private final StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @ModelAttribute("title")
    public String getPageTitle() {
        return "Store";
    }

    @GetMapping("")
    public String stores(Model model) {
        model.addAttribute("stores", storeService.findAll());
//        return "store/all";
        model.addAttribute("pageName", "store/all");
        return "common/header";
    }

    @GetMapping("/{storeId}")
    public String detail(@PathVariable int storeId, Model model) {
        model.addAttribute("store", storeService.findById(storeId));
//        return "store/detail";
        model.addAttribute("pageName", "store/detail");
        return "common/header";
    }

    @GetMapping("/new")
    public String addStore(Model model) {
        model.addAttribute("store", new Store());
        model.addAttribute("action", "new");
//        return "store/edit";
        model.addAttribute("pageName", "store/edit");
        return "common/header";
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
//        return "store/edit";
        model.addAttribute("pageName", "store/edit");
        return "common/header";
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
