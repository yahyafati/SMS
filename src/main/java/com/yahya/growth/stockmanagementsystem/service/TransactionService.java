package com.yahya.growth.stockmanagementsystem.service;

import com.yahya.growth.stockmanagementsystem.model.Transaction;

public interface TransactionService extends BasicServiceSkeleton<Transaction> {
    Transaction save(Transaction transaction, String[] items, String[] prices, String[] quantities);
}
