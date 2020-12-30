package com.yahya.growth.stockmanagementsystem.dao;

import com.yahya.growth.stockmanagementsystem.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierDao extends JpaRepository<Supplier, Integer> {
}
