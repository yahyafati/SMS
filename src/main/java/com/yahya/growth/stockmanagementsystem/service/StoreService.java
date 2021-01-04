package com.yahya.growth.stockmanagementsystem.service;

import com.yahya.growth.stockmanagementsystem.model.Store;

import java.util.List;

public interface StoreService {

    Store findById(int supplierId);

    Store save(Store store);

    List<Store> findAll();

    void deleteById(Integer supplierId);

}
