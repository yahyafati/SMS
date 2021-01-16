package com.yahya.growth.stockmanagementsystem.utilities;

import com.yahya.growth.stockmanagementsystem.model.Item;
import com.yahya.growth.stockmanagementsystem.model.ItemTransaction;
import com.yahya.growth.stockmanagementsystem.model.Transaction;
import com.yahya.growth.stockmanagementsystem.service.ItemTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class TransactionUtils {

    private final ItemTransactionService itemTransactionService;

    @Autowired
    public TransactionUtils(ItemTransactionService itemTransactionService) {
        this.itemTransactionService = itemTransactionService;
    }

    public boolean isTransactionUpdated(Transaction transaction) {
        for (ItemTransaction itemTransaction: transaction.getItemTransactions()) {
            if (itemTransaction.getInitialQuantity() > itemTransaction.getRemaining()) {
                return true;
            }
        }
        return true;
    }


    /**
     * Check if any of the items in this transaction is necessary for any of these transactions
     * @param transaction The Transaction to check
     * @return true if the transaction is necessary
     */
    public boolean isTransactionNecessary(Transaction transaction) {
        for (ItemTransaction itemTransaction : transaction.getItemTransactions()) {
            Item item = itemTransaction.getItem();
            /*
             * getQuantityOfItem - itemTransaction.getRemaining = (A) = The number of Items left in stock if this itemTransaction is not included.
             * itemTransaction.getInitialQuantity - itemTransaction.getRemaining = (B) = Items Sold from this itemTransaction.
             * TODO This doesn't consider reorder level
             * (A) must be greater than or equals to (B) for the item to be considered unnecessary
             * getQuantityOfItem >= itemTransaction.getInitialQuantity == this item transaction's unnecessariness
             * [Negating the above equation] getQuantityOfItem < itemTransaction.getInitialQuantity == this item transaction's necessity
             */
            if (itemTransactionService.getQuantityOfItem(item) < itemTransaction.getInitialQuantity()) {
                return true;
            }
        }
        return false; // Not Necessary
    }
}
