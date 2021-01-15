package com.yahya.growth.stockmanagementsystem.restController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yahya.growth.stockmanagementsystem.utilities.Company;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/company")
public class CompanyRestController {

    @GetMapping("")
    public Company getCompany() throws IOException {
        // TODO There must be a better way to do this
        File file = new File("src/main/resources/static/json/company.json");
//        System.out.println(file.getAbsolutePath());
        return new ObjectMapper().readValue(file, Company.class);
    }
}
