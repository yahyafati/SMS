package com.yahya.growth.stockmanagementsystem.service;

import com.yahya.growth.stockmanagementsystem.model.Transaction;
import com.yahya.growth.stockmanagementsystem.utilities.TransactionException;

public interface TransactionService extends BasicServiceSkeleton<Transaction> {
    Transaction save(Transaction transaction, String[] ids, String[] items, String[] prices, String[] quantities) throws TransactionException;
}
