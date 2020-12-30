package com.yahya.growth.stockmanagementsystem.service;

import com.yahya.growth.stockmanagementsystem.model.Supplier;

import java.util.List;

public interface SupplierService {

    Supplier findById(int supplierId);

    Supplier save(Supplier supplier);

    List<Supplier> findAll();

    void deleteById(Integer supplierId);

}
