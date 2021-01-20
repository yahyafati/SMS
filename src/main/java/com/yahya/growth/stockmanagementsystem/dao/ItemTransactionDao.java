package com.yahya.growth.stockmanagementsystem.dao;

import com.yahya.growth.stockmanagementsystem.model.Item;
import com.yahya.growth.stockmanagementsystem.model.ItemTransaction;
import com.yahya.growth.stockmanagementsystem.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemTransactionDao extends JpaRepository<ItemTransaction, Integer> {

    List<ItemTransaction> findAllByTransaction(Transaction transaction);

    List<ItemTransaction> findAllByItem(Item item);

    List<ItemTransaction> findAllByItemOrderByIdAsc(Item item);

    List<ItemTransaction> findAllByItemOrderByIdDesc(Item item);
//    List<ItemTransaction> findAllByItemOrderByOrderNoAsc(Item item);

    @Query("select sum(it.remainingQuantity) from ItemTransaction it where it.item = :item")
    Integer getItemSum(@Param("item") Item item);

    @Query("select sum(it.remainingQuantity) from ItemTransaction it where it.item.id = :id")
    Integer getItemSum(@Param("id") Integer id);

}
