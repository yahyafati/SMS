package com.yahya.growth.stockmanagementsystem.service.implematation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yahya.growth.stockmanagementsystem.service.CompanyService;
import com.yahya.growth.stockmanagementsystem.utilities.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class CompanyServiceImpl implements CompanyService {

    private static Company company;
    private final ObjectMapper mapper;
    private final File savedFile;

    @Autowired
    public CompanyServiceImpl() {
        mapper = new ObjectMapper();
        // TODO There must be a better way.
        savedFile = new File("src/main/resources/static/json/company.json");
    }

    private Company generateNewInstance() {
        try {
            company = mapper.readValue(savedFile, Company.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return company;
    }


    @Override
    public Company getCurrentCompany() {
        return generateNewInstance();
    }

    @Override
    public void saveCurrentCompany(Company company) {
        try {
            mapper.writeValue(savedFile, company);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
