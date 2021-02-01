package com.yahya.growth.stockmanagementsystem.service.implematation;

import com.yahya.growth.stockmanagementsystem.dao.CustomerDao;
import com.yahya.growth.stockmanagementsystem.model.Customer;
import com.yahya.growth.stockmanagementsystem.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDao customerDao;

    public CustomerServiceImpl(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public Customer findById(int customerId) {
        return customerDao.findById(customerId).orElseThrow(() -> {throw new IllegalArgumentException("The Item you are looking for is no longer available.");});
    }

    @Override
    public Customer save(Customer customer) {
        return customerDao.save(customer);
    }

    @Override
    public List<Customer> findAll() {
        return customerDao.findAll();
    }

    @Override
    public void deleteById(Integer customerId) {
        customerDao.deleteById(customerId);
    }
}
