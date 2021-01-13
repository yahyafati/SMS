package com.yahya.growth.stockmanagementsystem.service.implematation;

import com.yahya.growth.stockmanagementsystem.dao.TransactionDao;
import com.yahya.growth.stockmanagementsystem.model.Item;
import com.yahya.growth.stockmanagementsystem.model.ItemTransaction;
import com.yahya.growth.stockmanagementsystem.model.Transaction;
import com.yahya.growth.stockmanagementsystem.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionDao transactionDao;

    @Autowired
    public TransactionServiceImpl(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    @Override
    public Transaction findById(int id) {
        return transactionDao.findById(id).orElseThrow();
    }

    @Override
    public Transaction save(Transaction transaction) {
        transaction.setAddedTime(new Timestamp(System.currentTimeMillis()));
        return transactionDao.save(transaction);
    }

    @Override
    public List<Transaction> findAll() {
        return transactionDao.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        transactionDao.deleteById(id);
    }

    @Override
    public Transaction save(Transaction transaction, String[] ids, String[] items, String[] prices, String[] quantities) {
//        transaction = save(transaction);
        for (int i = 0; i < items.length; i++) {
            int id = Integer.parseInt(ids[i]);
            Item item = Item.builder().id(Integer.parseInt(items[i])).build();
            double price = Double.parseDouble(prices[i]);
            int quantity = Integer.parseInt(quantities[i]);
            ItemTransaction itemTransaction = ItemTransaction.builder()
                    .id(id)
                    .item(item)
                    .unitPrice(price)
                    .quantity(quantity)
                    .transaction(transaction)
                    .build();
            transaction.getItemTransactions().add(itemTransaction);
            System.out.printf("Item: %s,    Price: %s,    Quantity: %s\n", items[i], prices[i], quantities[i]);
        }
        return save(transaction);
    }
}
