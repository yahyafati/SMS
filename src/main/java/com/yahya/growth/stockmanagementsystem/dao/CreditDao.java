package com.yahya.growth.stockmanagementsystem.dao;

import com.yahya.growth.stockmanagementsystem.model.Credit;
import com.yahya.growth.stockmanagementsystem.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditDao extends JpaRepository<Credit, Integer> {

    @Query("select credit from Credit credit where  credit.transaction.customer = :customer")
    List<Credit> findAllByCustomer(@Param("customer") Customer customer);

//    @Query("select sum(credit.remainingCredit) from Credit credit where  credit.itemTransaction.transaction.fromTo = :customer")
//    Double getCreditRemainingByCustomer(@Param("customer") Customer customer);

}
