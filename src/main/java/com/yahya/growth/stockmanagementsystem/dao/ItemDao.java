package com.yahya.growth.stockmanagementsystem.dao;

import com.yahya.growth.stockmanagementsystem.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemDao extends JpaRepository<Item, Integer> {

    List<Item> findAllByQuantityGreaterThan(int quantity);

}
