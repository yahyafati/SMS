package com.yahya.growth.stockmanagementsystem.restController;

import com.yahya.growth.stockmanagementsystem.model.Customer;
import com.yahya.growth.stockmanagementsystem.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerRestController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("")
    public List<Customer> allCustomers() {
        return customerService.findAll();
    }

    @GetMapping("/{customerId}")
    public Customer getCustomer(@PathVariable int customerId) {
        return customerService.findById(customerId);
    }

    @PostMapping("")
    public Customer addCustomer(@RequestBody Customer customer) {
        return customerService.save(customer);
    }

    @PutMapping("/{customerId}")
    public Customer updateCustomer(@PathVariable int customerId, @RequestBody Customer customer) {
        customer.setId(customerId);
        return customerService.save(customer);
    }

    @DeleteMapping("/{customerId}")
    public String deleteCustomer(@PathVariable int customerId) {
        customerService.deleteById(customerId);
        return "Customer has been deleted";
    }

}
