package com.nagp.springboot_mongodb.controller;

import com.nagp.springboot_mongodb.model.Product;
import com.nagp.springboot_mongodb.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MainController {

    @Autowired
    ProductRepo productRepo;

    @PostMapping("/addProduct")
    public void addProduct(@RequestBody Product product) {
        productRepo.save(product);
    }

    @GetMapping("/getProduct/{id}")
    public Product getProduct(@PathVariable Integer productId) {
        return productRepo.findById(productId).orElse(null);
    }

    @GetMapping("/getProducts")
    public List<Product> getProducts() {
        return productRepo.findAll();
    }
}
