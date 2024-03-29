package com.yahya.growth.stockmanagementsystem.service;
import com.yahya.growth.stockmanagementsystem.utilities.TransactionException;

import java.util.List;

public interface BasicServiceSkeleton<T> {

    T findById(int id);

    T save(T item);

    List<T> findAll();

    boolean deleteById(Integer id);
}
