package com.example.demo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//import com.example.demo.dto.ProductDTO;
import com.example.demo.model.CartItem;
import com.example.demo.service.CartItemService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cart")
public class CartItemController {

    @Autowired
    CartItemService service;

    @PostMapping("/add")
    public String addToCart(@Valid @RequestBody CartItem cartItem) {
        return service.addCartItem(cartItem);
    }

    @PutMapping("/update")
    public CartItem updateCart(@Valid @RequestBody CartItem cartItem) {
        return service.updateCartItem(cartItem);
    }

    @GetMapping("/fetchByCartItemId/{id}")
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

    @GetMapping("/fetchByUserId/{userId}")
    public List<CartItem> getByProductId(@PathVariable("userId") int userId) {
        return service.getCartItemsByUserId(userId);
    }
    
//    @GetMapping("/fetchProductDetails/{productId}")
//    public ProductDTO fetchProductDetails(@PathVariable int productId) {
//        return service.fetchProductDetails(productId);
//    }
    
}