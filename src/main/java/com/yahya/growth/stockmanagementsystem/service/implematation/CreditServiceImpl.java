package com.yahya.growth.stockmanagementsystem.service.implematation;

import com.yahya.growth.stockmanagementsystem.dao.CreditDao;
import com.yahya.growth.stockmanagementsystem.model.Credit;
import com.yahya.growth.stockmanagementsystem.model.Customer;
import com.yahya.growth.stockmanagementsystem.model.Transaction;
import com.yahya.growth.stockmanagementsystem.model.TransactionType;
import com.yahya.growth.stockmanagementsystem.service.CreditService;
import com.yahya.growth.stockmanagementsystem.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    private Map<Customer, Double> findAllGrouped(TransactionType type) {
        List<Credit> credits = findAll();
        return credits.stream()
                .filter(credit -> credit.getTransaction().getType() == type)
                .collect(
                        Collectors.groupingBy(credit -> credit.getTransaction().getCustomer(),
                                Collectors.summingDouble(Credit::getInitialAmount))
                );
    }

    @Override
    public Map<Customer, Double> findAllPayablePerCustomer() {
        return findAllGrouped(TransactionType.PURCHASE);
    }

    @Override
    public Map<Customer, Double> findAllReceivablePerCustomer() {
        return findAllGrouped(TransactionType.SALE);
    }

    @Override
    public List<Credit> findByCustomer(Customer customer) {
        return creditDao.findAllByCustomer(customer);
    }

    @Override
    public List<Credit> findByTransaction(Transaction transaction) {
        return null;
    }
}
