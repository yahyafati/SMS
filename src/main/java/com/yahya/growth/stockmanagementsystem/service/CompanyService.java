package com.yahya.growth.stockmanagementsystem.service;

import com.yahya.growth.stockmanagementsystem.utilities.Company;

public interface CompanyService {

    Company getCurrentCompany();

    void saveCurrentCompany(Company company);

}
