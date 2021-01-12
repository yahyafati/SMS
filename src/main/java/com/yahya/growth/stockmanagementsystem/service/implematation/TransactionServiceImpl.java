package com.yahya.growth.stockmanagementsystem.service.implematation;

import com.yahya.growth.stockmanagementsystem.dao.TransactionDao;
import com.yahya.growth.stockmanagementsystem.model.Transaction;
import com.yahya.growth.stockmanagementsystem.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionDao transactionDao;

    @Autowired
    public TransactionServiceImpl(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    @Override
    public Transaction findById(int id) {
        return transactionDao.findById(id).orElseThrow();
    }

    @Override
    public Transaction save(Transaction item) {
        return transactionDao.save(item);
    }

    @Override
    public List<Transaction> findAll() {
        return transactionDao.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        transactionDao.deleteById(id);
    }
}
