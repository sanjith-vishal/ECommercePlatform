package com.example.demo.service;

import java.util.List;
import com.example.demo.model.CartItem;

public interface CartItemService {

    public abstract String addCartItem(CartItem item);
    public abstract CartItem updateCartItem(CartItem item);
    public abstract CartItem getCartItemById(int cartItemId);
    public abstract List<CartItem> getAllCartItems();
    public abstract String deleteCartItemById(int cartItemId);
    public abstract List<CartItem> getItemsByProductId(int productId);
}