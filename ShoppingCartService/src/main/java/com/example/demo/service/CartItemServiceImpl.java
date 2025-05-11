package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CartItemWithProductDTO;
import com.example.demo.dto.CartItemWithUserDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.exceptions.CartItemNotFoundException;
import com.example.demo.feign.ProductClient;
import com.example.demo.feign.UserClient;
import com.example.demo.model.CartItem;
import com.example.demo.repository.CartItemRepository;

@Service
public class CartItemServiceImpl implements CartItemService {

    private static final Logger log = LoggerFactory.getLogger(CartItemServiceImpl.class);

    @Autowired
    private ProductClient productClient;

    @Autowired
    CartItemRepository repository;

    @Autowired
    private UserClient userClient;

    @Override
    public String addCartItem(CartItem cartItem) {
        log.info("Adding cart item for productId={} and userId={}", cartItem.getProductId(), cartItem.getUserId());
        ProductDTO product = productClient.getProductById(cartItem.getProductId());
        double totalPrice = product.getPrice() * cartItem.getQuantity();
        cartItem.setTotalPrice(totalPrice);
        repository.save(cartItem);
        log.info("Cart item added successfully with totalPrice={}", totalPrice);
        return "Item added to cart successfully.";
    }

    @Override
    public CartItem updateCartItem(CartItem cartItem) {
        log.info("Updating cart item with ID: {}", cartItem.getCartItemId());
        return repository.save(cartItem);
    }

    @Override
    public CartItem getCartItemById(int cartItemId) {
        log.info("Fetching cart item with ID: {}", cartItemId);
        return repository.findById(cartItemId)
                .orElseThrow(() -> new CartItemNotFoundException("Cart item not found with ID: " + cartItemId));
    }

    @Override
    public List<CartItem> getAllCartItems() {
        log.info("Fetching all cart items");
        List<CartItem> items = repository.findAll();
        log.info("Total cart items fetched: {}", items.size());
        return items;
    }

    @Override
    public String deleteCartItemById(int cartItemId) {
        log.info("Attempting to delete cart item with ID: {}", cartItemId);
        if (!repository.existsById(cartItemId)) {
        	log.warn("Cart item not found for ID: {}", cartItemId);
            throw new CartItemNotFoundException("Cannot delete. Cart item not found with ID: " + cartItemId);
        }
        repository.deleteById(cartItemId);
        log.info("Cart item deleted: {}", cartItemId);
        return "Cart item deleted.";
        
    }

    @Override
    public List<CartItem> getCartItemsByUserId(int userId) {
        log.info("Fetching cart items for user ID: {}", userId);
        List<CartItem> items = repository.findByUserId(userId);
        log.info("Total cart items for user {}: {}", userId, items.size());
        return items;
    }

    @Override
    public ProductDTO fetchProductDetails(int productId) {
        log.info("Fetching product details for product ID: {}", productId);
        return productClient.getProductById(productId);
    }

    @Override
    public CartItemWithProductDTO getCartItemWithProduct(int cartItemId) {
        log.info("Fetching cart item with product for cartItemId={}", cartItemId);
        CartItem cartItem = repository.findById(cartItemId)
                .orElseThrow(() -> {
                    log.error("CartItem not found: {}", cartItemId);
                    return new RuntimeException("CartItem not found");
                });

        ProductDTO productDTO = productClient.getProductById(cartItem.getProductId());

        CartItemWithProductDTO dto = new CartItemWithProductDTO();
        dto.setCartItemId(cartItem.getCartItemId());
        dto.setProductId(cartItem.getProductId());
        dto.setQuantity(cartItem.getQuantity());
        dto.setProduct(productDTO);

        log.info("Returning cart item with product details");
        return dto;
    }

    @Override
    public List<CartItemWithProductDTO> getCartItemsWithProductsByUserId(int userId) {
        log.info("Fetching cart items with products for userId={}", userId);
        List<CartItem> cartItems = repository.findByUserId(userId);

        return cartItems.stream().map(item -> {
            ProductDTO productDTO = productClient.getProductById(item.getProductId());

            CartItemWithProductDTO dto = new CartItemWithProductDTO();
            dto.setCartItemId(item.getCartItemId());
            dto.setProductId(item.getProductId());
            dto.setQuantity(item.getQuantity());
            dto.setProduct(productDTO);

            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<CartItemWithUserDTO> getCartItemsWithUser(int userId) {
        log.info("Fetching cart items with user for userId={}", userId);
        List<CartItem> cartItems = repository.findByUserId(userId);
        UserDTO user = userClient.getUserById(userId);

        List<CartItemWithUserDTO> result = new ArrayList<>();
        for (CartItem item : cartItems) {
            CartItemWithUserDTO dto = new CartItemWithUserDTO();
            dto.setCartItemId(item.getCartItemId());
            dto.setUserId(item.getUserId());
            dto.setProductId(item.getProductId());
            dto.setQuantity(item.getQuantity());
            dto.setTotalPrice(item.getTotalPrice());
            dto.setUser(user);

            result.add(dto);
        }
 
        log.info("Returning {} cart items with user info", result.size());
        return result;
    }
}