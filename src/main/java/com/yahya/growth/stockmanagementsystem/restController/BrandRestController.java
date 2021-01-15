package com.yahya.growth.stockmanagementsystem.restController;

import com.yahya.growth.stockmanagementsystem.model.Brand;
import com.yahya.growth.stockmanagementsystem.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brand")
public class BrandRestController {

    @Autowired
    private BrandService brandService;

    @GetMapping("")
    public List<Brand> getAllBrands() {
        return brandService.findAll();
    }

    @GetMapping("/{brand_id}")
    public Brand getBrand(@PathVariable(name = "brand_id") int brandId) {
        return brandService.findById(brandId);
    }

    @PostMapping("")
    public Brand addBrand(@RequestBody Brand brand) {
        return brandService.save(brand);
    }

    @PutMapping("/{brand_id}")
    public Brand updateBrand(@PathVariable(name = "brand_id") int brandId, @RequestBody Brand brand) {
        brand.setId(brandId);
        return brandService.save(brand);
    }

    @DeleteMapping("/{brand_id}")
    public String deleteBrand(@PathVariable(name = "brand_id") int brandId) {
        brandService.deleteById(brandId);
        return "Branch has been deleted";
    }


}
