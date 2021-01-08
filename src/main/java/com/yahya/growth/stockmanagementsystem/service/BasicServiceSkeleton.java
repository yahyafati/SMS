package com.yahya.growth.stockmanagementsystem.service;
import java.util.List;

public interface BasicServiceSkeleton<T> {

    T findById(int id);

    T save(T item);

    List<T> findAll();

    void deleteById(Integer id);
}
