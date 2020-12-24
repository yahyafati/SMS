package com.yahya.growth.stockmanagementsystem.service;

import com.yahya.growth.stockmanagementsystem.model.Item;

import java.util.List;

public interface ItemService {

    Item findById(int itemId);

    Item save(Item item);

    List<Item> findAll();

    void deleteById(Integer itemId);
}
