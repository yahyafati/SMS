package com.yahya.growth.stockmanagementsystem.restController;

import com.yahya.growth.stockmanagementsystem.model.Item;
import com.yahya.growth.stockmanagementsystem.service.CategoryService;
import com.yahya.growth.stockmanagementsystem.service.ItemService;
import com.yahya.growth.stockmanagementsystem.service.SubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
public class ItemRestController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SubcategoryService subcategoryService;

    @GetMapping("")
    public List<Item> getAllItems() {
        return itemService.findAll();
    }

    @GetMapping("/{item_id}")
    public Item getItem(@PathVariable(name = "item_id") Integer itemId) {
        return itemService.findById(itemId);
    }

    @PostMapping("/")
    public Item addItem(@RequestBody Item item) {
        return itemService.save(item);
    }

    @PutMapping("/{item_id}")
    public Item updateItem(@RequestBody Item item, @PathVariable(name = "item_id") Integer itemId) {
        item.setId(itemId);
        return itemService.save(item);
    }

    @DeleteMapping("/{item_id}")
    public String deleteItem(@PathVariable(name = "item_id") Integer itemId) {
        itemService.deleteById(itemId);
        return "Item has been deleted";
    }

}
