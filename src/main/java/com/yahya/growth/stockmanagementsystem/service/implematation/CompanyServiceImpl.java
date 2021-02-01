package com.yahya.growth.stockmanagementsystem.service.implematation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yahya.growth.stockmanagementsystem.service.CompanyService;
import com.yahya.growth.stockmanagementsystem.model.utility.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class CompanyServiceImpl implements CompanyService {

    private static Company company;
    private final ObjectMapper mapper;
    private final File savedFile;

    private final Path filePath = Paths.get(System.getProperty("user.home"), "HelioIMS", "company.json");

    @Autowired
    public CompanyServiceImpl() throws IOException {
        mapper = new ObjectMapper();
        savedFile = new File(filePath.toString());
        Files.createDirectories(filePath.getParent());
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
            File initialFile = ResourceUtils.getFile("classpath:json/company.json");
            Files.writeString(filePath, Files.readString(initialFile.toPath()));
        }
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
