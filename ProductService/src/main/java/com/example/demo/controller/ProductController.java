package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService service;

    @PostMapping("/save")
    public String saveProduct(@RequestBody Product product) {
        return service.saveProduct(product);
    }

    @PutMapping("/update")
    public Product updateProduct(@RequestBody Product product) {
        return service.updateProduct(product);
    }

    @GetMapping("/fetchById/{id}")
    public Product getProduct(@PathVariable("id") int productId) {
        return service.getProduct(productId);
    }

    @GetMapping("/fetchAll")
    public List<Product> getAllProducts() {
        return service.getAllProducts();
    }
}