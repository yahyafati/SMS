package com.yahya.growth.stockmanagementsystem.service.implematation;

import com.yahya.growth.stockmanagementsystem.dao.ItemDao;
import com.yahya.growth.stockmanagementsystem.model.Item;
import com.yahya.growth.stockmanagementsystem.service.ItemService;
import com.yahya.growth.stockmanagementsystem.service.ItemTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemDao itemDao;
    private final ItemTransactionService itemTransactionService;

    @Autowired
    public ItemServiceImpl(ItemDao itemDao, ItemTransactionService itemTransactionService) {
        this.itemDao = itemDao;
        this.itemTransactionService = itemTransactionService;
    }

    @Override
    public Item findById(int itemId) {
        return itemDao.findById(itemId).orElseThrow(() -> {throw new IllegalArgumentException("The Item you are looking for is no longer available.");});
    }

    @Override
    public Item save(Item item) {
        return itemDao.save(item);
    }

    @Override
    public List<Item> findAll() {
        return itemDao.findAll();
    }

    /**
     * @return List of Available Items in Stock right now
     */
    @Override
    public List<Item> findAllAvailableItems() {
        return itemDao.findAll()
                .stream()
                .filter(item -> itemTransactionService.getQuantityOfItem(item) > 0)
                .collect(Collectors.toList());
//        return itemDao.findAllByQuantityGreaterThan(0);
    }

    @Override
    public void deleteById(Integer itemId) {
        itemDao.deleteById(itemId);
    }

}
