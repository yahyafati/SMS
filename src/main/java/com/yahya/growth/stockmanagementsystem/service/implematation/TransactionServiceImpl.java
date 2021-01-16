package com.yahya.growth.stockmanagementsystem.service.implematation;

import com.yahya.growth.stockmanagementsystem.dao.TransactionDao;
import com.yahya.growth.stockmanagementsystem.model.Item;
import com.yahya.growth.stockmanagementsystem.model.ItemTransaction;
import com.yahya.growth.stockmanagementsystem.model.Transaction;
import com.yahya.growth.stockmanagementsystem.model.TransactionType;
import com.yahya.growth.stockmanagementsystem.service.ItemTransactionService;
import com.yahya.growth.stockmanagementsystem.service.TransactionService;
import com.yahya.growth.stockmanagementsystem.utilities.TransactionException;
import com.yahya.growth.stockmanagementsystem.utilities.TransactionUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionDao transactionDao;
    private final ItemTransactionService itemTransactionService;
    private final TransactionUtils transactionUtils;

    @Autowired
    public TransactionServiceImpl(TransactionDao transactionDao, ItemTransactionService itemTransactionService, TransactionUtils transactionUtils) {
        this.transactionDao = transactionDao;
        this.itemTransactionService = itemTransactionService;
        this.transactionUtils = transactionUtils;
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
        delete(findById(id));
    }

    // TODO Urgent Remove SneakyThrows. Remove them!
    @SneakyThrows
    public void delete(Transaction transaction) {

        if (transaction.getType() == TransactionType.SALE) {
            preDeleteSale(transaction);
        } else {
            preDeletePurchase(transaction);
        }
        transactionDao.delete(transaction);
    }

    /**
     * Performs pre delete operations of Transactions of TransactionType == PURCHASE.
     * @param transaction The transaction to be deleted.
     * @throws TransactionException
     */
    private void preDeletePurchase(Transaction transaction) throws TransactionException {
        assert transaction.getType() == TransactionType.PURCHASE;
        if (!transactionUtils.isTransactionUpdated(transaction)) {
            return;
        }
        if (transactionUtils.isTransactionNecessary(transaction)) {
            throw new TransactionException("You can not delete a necessary transaction. This transaction is necessary. TODO Make this message a bit more clearer later on");
        }

        // itemTransaction - ItemTransaction in the Transaction to be deleted
        for (ItemTransaction itemTransaction : transaction.getItemTransactions()) {
            /*
             * Gets all transaction in ascending order.
             * Remove item from other ItemTransaction(iTransaction) and add it into current ItemTransaction(itemTransaction)
             * until the itemTransaction.initialQuantity == itemTransaction.remainingQuantity (Returned to Original State - no-update stated)
             */
            List<ItemTransaction> curItemTransactions = itemTransactionService.findAllByItemSorted(itemTransaction.getItem());
            for (ItemTransaction iTransaction: curItemTransactions) {
                if (iTransaction.getTransaction().getType() == TransactionType.SALE
                        || iTransaction.getTransaction().getId() == transaction.getId()) {
                    continue;
                }
                if (itemTransaction.getAmountSold() < iTransaction.getRemaining()) {
                    iTransaction.setRemaining(iTransaction.getRemaining() - itemTransaction.getAmountSold());
                    itemTransaction.setRemaining(itemTransaction.getInitialQuantity());
                } else {
                    itemTransaction.setRemaining(itemTransaction.getRemaining() + iTransaction.getRemaining());
                    iTransaction.setRemaining(0);
                }
                // Break once the amountSold(= initialQuantity - remaining) == 0
                if (itemTransaction.getAmountSold() == 0) {
                    break;
                }
            }
            itemTransactionService.saveAll(curItemTransactions);
        }
        assert transactionUtils.isTransactionNecessary(transaction) : "Transaction is supposed to be unnecessary before being deleted.";
    }

    /**
     *
     * This is a reverse sale function.
     * It gets ItemTransaction with TransactionType == Purchase Type, for each item in <b>reverse order</b> from DB.
     * For each ItemTransaction it will add on to the remaining until the 'remaining' is full (equal to 'initialQuantity')
     * @param transaction Transaction of type Sales to be deleted
     *
     */
    private void preDeleteSale(Transaction transaction) {
        assert transaction.getType() == TransactionType.SALE;
        for (ItemTransaction itemTransaction : transaction.getItemTransactions()) {

            List<ItemTransaction> curItemTransactions = itemTransactionService.findAllByItemSortedDescending(itemTransaction.getItem());
            curItemTransactions = curItemTransactions
                    .stream()
                    .filter(iTransaction -> iTransaction.getTransaction().getType() == TransactionType.PURCHASE)
                    .filter(iTransaction -> iTransaction.getRemaining() < iTransaction.getInitialQuantity())
                    .collect(Collectors.toList());
            int quantity = itemTransaction.getInitialQuantity();
            // For each itemTransaction in curItemTransactions
            for (ItemTransaction iTransaction: curItemTransactions) {
                // iTransaction.getQuantity() - iTransaction.getRemaining() = the amount sold from this iTransaction
                if (iTransaction.getInitialQuantity() - iTransaction.getRemaining() < quantity) {
                    quantity -= iTransaction.getInitialQuantity() - iTransaction.getRemaining();
                    iTransaction.setRemaining(iTransaction.getInitialQuantity());
                } else {
                    iTransaction.setRemaining(iTransaction.getRemaining() + quantity);
                    quantity = 0;
                }
            }

            itemTransactionService.saveAll(curItemTransactions);
        }
    }

    @Override
    public Transaction save(Transaction transaction, String[] ids, String[] items, String[] prices, String[] quantities) throws TransactionException {
        if (transaction.getType() == TransactionType.PURCHASE) {
            transaction = savePurchase(transaction, ids, items, prices, quantities);
        } else {
            transaction = saveSale(transaction, ids, items, prices, quantities);
        }
        return transaction;
    }

    private ItemTransaction buildItemTransactions(Transaction transaction, String id, String item, String price, String quantity) {
        return ItemTransaction.builder()
                .id(Integer.parseInt(id))
                .item(Item.builder().id(Integer.parseInt(item)).build())
                .unitPrice(Float.parseFloat(price))
                .initialQuantity(Integer.parseInt(quantity))
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
                    iTransaction.setRemaining(iTransaction.getInitialQuantity() - quantity);
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
            itemTransaction.setRemaining(itemTransaction.getInitialQuantity());
            transaction.getItemTransactions().add(itemTransaction);
            System.out.printf("Item: %s,    Price: %s,    Quantity: %s\n", items[i], prices[i], quantities[i]);
        }
        return save(transaction);
    }
}
