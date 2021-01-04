package com.yahya.growth.stockmanagementsystem.dao;

import com.yahya.growth.stockmanagementsystem.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierDao extends JpaRepository<Store, Integer> {
}
