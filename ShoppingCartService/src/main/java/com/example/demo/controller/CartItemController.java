package com.example.demo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.CartItem;
import com.example.demo.service.CartItemService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cart")
public class CartItemController {

    @Autowired
    CartItemService service;

    @PostMapping("/add")
    public String addToCart(@Valid @RequestBody CartItem item) {
        return service.addCartItem(item);
    }

    @PutMapping("/update")
    public CartItem updateCart(@Valid @RequestBody CartItem item) {
        return service.updateCartItem(item);
    }

    @GetMapping("/fetchById/{id}")
    public CartItem getCartItem(@PathVariable("id") int id) {
        return service.getCartItemById(id);
    }

    @GetMapping("/fetchAll")
    public List<CartItem> getAllCartItems() {
        return service.getAllCartItems();
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCartItem(@PathVariable("id") int id) {
        return service.deleteCartItemById(id);
    }

    @GetMapping("/byProduct/{productId}")
    public List<CartItem> getByProductId(@PathVariable int productId) {
        return service.getItemsByProductId(productId);
    }
}