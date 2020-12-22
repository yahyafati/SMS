package com.yahya.growth.stockmanagementsystem.service;

import com.yahya.growth.stockmanagementsystem.model.Item;

public interface ItemService {

    Item findById(int itemId);

    int save(Item item);

}
