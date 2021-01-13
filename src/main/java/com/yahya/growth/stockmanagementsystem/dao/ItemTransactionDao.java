package com.yahya.growth.stockmanagementsystem.dao;

import com.yahya.growth.stockmanagementsystem.model.Item;
import com.yahya.growth.stockmanagementsystem.model.ItemTransaction;
import com.yahya.growth.stockmanagementsystem.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemTransactionDao extends JpaRepository<ItemTransaction, Integer> {

    List<ItemTransaction> findAllByTransaction(Transaction transaction);

    List<ItemTransaction> findAllByItem(Item item);

}
