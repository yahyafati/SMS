package com.yahya.growth.stockmanagementsystem.service;

import com.yahya.growth.stockmanagementsystem.model.Item;
import com.yahya.growth.stockmanagementsystem.model.ItemTransaction;
import com.yahya.growth.stockmanagementsystem.model.Transaction;

import java.util.Collection;
import java.util.List;

public interface ItemTransactionService extends BasicServiceSkeleton<ItemTransaction> {

    List<ItemTransaction> findAllByTransactionId(int transactionId);

    List<ItemTransaction> findAllByTransaction(Transaction transaction);

    List<ItemTransaction> findAllByItemId(int itemId);

    List<ItemTransaction> findAllByItem(Item item);

    List<ItemTransaction> findAllByItemSorted(Item item);

    List<ItemTransaction> findAllByItemIdSorted(int itemId);

    int getQuantityOfItem(Item item);

    int getQuantityOfItem(int id);

    List<ItemTransaction> saveAll(Collection<ItemTransaction> itemTransactions);
}
