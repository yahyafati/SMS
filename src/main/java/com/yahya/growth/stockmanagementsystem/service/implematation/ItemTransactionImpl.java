package com.yahya.growth.stockmanagementsystem.service.implematation;

import com.yahya.growth.stockmanagementsystem.dao.ItemTransactionDao;
import com.yahya.growth.stockmanagementsystem.model.Item;
import com.yahya.growth.stockmanagementsystem.model.ItemTransaction;
import com.yahya.growth.stockmanagementsystem.model.Transaction;
import com.yahya.growth.stockmanagementsystem.service.ItemTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemTransactionImpl implements ItemTransactionService {

    private final ItemTransactionDao itemTransactionDao;

    @Autowired
    public ItemTransactionImpl(ItemTransactionDao itemTransactionDao) {
        this.itemTransactionDao = itemTransactionDao;
    }

    @Override
    public ItemTransaction findById(int id) {
        return itemTransactionDao.findById(id).orElseThrow();
    }

    @Override
    public ItemTransaction save(ItemTransaction item) {
        return itemTransactionDao.save(item);
    }

    @Override
    public List<ItemTransaction> findAll() {
        return itemTransactionDao.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        itemTransactionDao.deleteById(id);
    }

    @Override
    public List<ItemTransaction> findAllByTransactionId(int transactionId) {
        return null;
    }

    @Override
    public List<ItemTransaction> findAllByTransaction(Transaction transaction) {
        return itemTransactionDao.findAllByTransaction(transaction);
    }

    @Override
    public List<ItemTransaction> findAllByItemId(int itemId) {
        return null;
    }

    @Override
    public List<ItemTransaction> findAllByItem(Item item) {
        return itemTransactionDao.findAllByItem(item);
    }
}
