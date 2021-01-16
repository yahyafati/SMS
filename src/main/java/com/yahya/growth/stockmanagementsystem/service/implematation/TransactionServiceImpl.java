package com.yahya.growth.stockmanagementsystem.service.implematation;

import com.yahya.growth.stockmanagementsystem.dao.TransactionDao;
import com.yahya.growth.stockmanagementsystem.model.Item;
import com.yahya.growth.stockmanagementsystem.model.ItemTransaction;
import com.yahya.growth.stockmanagementsystem.model.Transaction;
import com.yahya.growth.stockmanagementsystem.model.TransactionType;
import com.yahya.growth.stockmanagementsystem.service.ItemTransactionService;
import com.yahya.growth.stockmanagementsystem.service.TransactionService;
import com.yahya.growth.stockmanagementsystem.utilities.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionDao transactionDao;
    private final ItemTransactionService itemTransactionService;

    @Autowired
    public TransactionServiceImpl(TransactionDao transactionDao, ItemTransactionService itemTransactionService) {
        this.transactionDao = transactionDao;
        this.itemTransactionService = itemTransactionService;
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
        if (transaction.getType() == TransactionType.PURCHASE) {
            transaction = savePurchase(transaction, ids, items, prices, quantities);
        } else {
            try {
                transaction = saveSale(transaction, ids, items, prices, quantities);
            } catch (TransactionException e) {
                e.printStackTrace();
            }
        }
        return transaction;
    }

    private ItemTransaction buildItemTransactions(Transaction transaction, String id, String item, String price, String quantity) {
        return ItemTransaction.builder()
                .id(Integer.parseInt(id))
                .item(Item.builder().id(Integer.parseInt(item)).build())
                .unitPrice(Float.parseFloat(price))
                .quantity(Integer.parseInt(quantity))
                .transaction(transaction)
                .build();
    }

    private Transaction saveSale(Transaction transaction, String[] ids, String[] items, String[] prices, String[] quantities) throws TransactionException {
        for (int i = 0; i < items.length; i++) {
            ItemTransaction itemTransaction = buildItemTransactions(transaction, ids[i], items[i], prices[i], quantities[i]);

            Item item = Item.builder().id(Integer.parseInt(items[i])).build();
            int quantity = Integer.parseInt(quantities[i]);
            int remainingQuantity =itemTransactionService.getQuantityOfItem(item);

            if (remainingQuantity < quantity) {
                throw new TransactionException(String.format("Sold quantity (%d) is greater than remaining quantity (%d) for %s",
                        quantity, remainingQuantity, item.getName()));
            }

            List<ItemTransaction> itemTransactions = itemTransactionService.findAllByItemSorted(item);
            for (ItemTransaction iTransaction : itemTransactions) {
                int remaining = iTransaction.getRemaining();
                if (remaining < quantity) {
                    quantity -= remaining;
                    iTransaction.setRemaining(0);
                } else {
                    iTransaction.setRemaining(iTransaction.getQuantity() - quantity);
                    quantity = 0;
                }
                if (quantity == 0) {
                    break;
                }
            }
            itemTransactionService.saveAll(itemTransactions);
            transaction.getItemTransactions().add(itemTransaction);
            System.out.printf("Item: %s,    Price: %s,    Quantity: %s\n", items[i], prices[i], quantities[i]);
        }
        return save(transaction);
    }

    private Transaction savePurchase(Transaction transaction, String[] ids, String[] items, String[] prices, String[] quantities) {
        for (int i = 0; i < items.length; i++) {
            ItemTransaction itemTransaction = buildItemTransactions(transaction, ids[i], items[i], prices[i], quantities[i]);
            itemTransaction.setRemaining(itemTransaction.getQuantity());
            transaction.getItemTransactions().add(itemTransaction);
            System.out.printf("Item: %s,    Price: %s,    Quantity: %s\n", items[i], prices[i], quantities[i]);
        }
        return save(transaction);
    }
}
