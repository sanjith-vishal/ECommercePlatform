package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.CartItem;
import com.example.demo.repository.CartItemRepository;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    CartItemRepository repository;

    @Override
    public String addCartItem(CartItem item) {
        repository.save(item);
        return "Item added to cart successfully.";
    }

    @Override
    public CartItem updateCartItem(CartItem item) {
        return repository.save(item);
    }

    @Override
    public CartItem getCartItemById(int cartItemId) {
        Optional<CartItem> optional = repository.findById(cartItemId);
        return optional.orElse(null);
    }

    @Override
    public List<CartItem> getAllCartItems() {
        return repository.findAll();
    }

    @Override
    public String deleteCartItemById(int cartItemId) {
        if (repository.existsById(cartItemId)) {
            repository.deleteById(cartItemId);
            return "Cart item deleted.";
        } else {
            return "Cart item not found.";
        }
    }

    @Override
    public List<CartItem> getItemsByProductId(int productId) {
        return repository.findByProductId(productId);
    }
}