package com.yahya.growth.stockmanagementsystem.service;

import com.yahya.growth.stockmanagementsystem.model.utility.Company;

public interface CompanyService {

    Company getCurrentCompany();

    void saveCurrentCompany(Company company);

}
