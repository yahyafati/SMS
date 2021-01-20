package com.yahya.growth.stockmanagementsystem.service.implematation;

import com.yahya.growth.stockmanagementsystem.dao.CreditDao;
import com.yahya.growth.stockmanagementsystem.model.Credit;
import com.yahya.growth.stockmanagementsystem.model.Customer;
import com.yahya.growth.stockmanagementsystem.model.Transaction;
import com.yahya.growth.stockmanagementsystem.service.CreditService;
import com.yahya.growth.stockmanagementsystem.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditServiceImpl implements CreditService {

    private final CreditDao creditDao;
    private final TransactionService transactionService;

    @Autowired
    public CreditServiceImpl(CreditDao creditDao, TransactionService transactionService) {
        this.creditDao = creditDao;
        this.transactionService = transactionService;
    }

    @Override
    public Credit findById(int id) {
        return creditDao.findById(id).orElseThrow();
    }

    @Override
    public Credit save(Credit item) {
        return creditDao.save(item);
    }

    @Override
    public List<Credit> findAll() {
        return creditDao.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        creditDao.deleteById(id);
    }

}
