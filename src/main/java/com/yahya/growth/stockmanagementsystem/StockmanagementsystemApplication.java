package com.yahya.growth.stockmanagementsystem;

import com.yahya.growth.stockmanagementsystem.model.Brand;
import com.yahya.growth.stockmanagementsystem.model.Category;
import com.yahya.growth.stockmanagementsystem.model.Subcategory;
import com.yahya.growth.stockmanagementsystem.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StockmanagementsystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockmanagementsystemApplication.class, args);
    }

    @Bean
    public CommandLineRunner demoData(
            CategoryService categoryService, SubcategoryService subcategoryService, BrandService brandService,
            SupplierService supplierService, ItemService itemService) {
        return args -> {

        };
    }

}
