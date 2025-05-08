package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CartItemWithProductDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.feign.ProductClient;
import com.example.demo.model.CartItem;
import com.example.demo.repository.CartItemRepository;

@Service
public class CartItemServiceImpl implements CartItemService {
	
	@Autowired
	private ProductClient productClient;

    @Autowired
    CartItemRepository repository;

    @Override
    public String addCartItem(CartItem cartItem) {
        repository.save(cartItem);
        return "Item added to cart successfully.";
    }

    @Override
    public CartItem updateCartItem(CartItem cartItem) {
        return repository.save(cartItem);
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
    public List<CartItem> getCartItemsByUserId(int userId) {
        return repository.findByUserId(userId);
    }
    
    @Override
    public ProductDTO fetchProductDetails(int productId) {
    	return productClient.getProductById(productId);
    }

    @Override
    public CartItemWithProductDTO getCartItemWithProduct(int cartItemId) {
        CartItem cartItem = repository.findById(cartItemId)
            .orElseThrow(() -> new RuntimeException("CartItem not found"));

        ProductDTO productDTO = productClient.getProductById(cartItem.getProductId());

        CartItemWithProductDTO dto = new CartItemWithProductDTO();
        dto.setCartItemId(cartItem.getCartItemId() );
        dto.setProductId(cartItem.getProductId());
        dto.setQuantity(cartItem.getQuantity());
        dto.setProduct(productDTO);

        return dto;
    }

    @Override
    public List<CartItemWithProductDTO> getCartItemsWithProductsByUserId(int userId) {
        List<CartItem> cartItems = repository.findByUserId(userId);

        return cartItems.stream().map(item -> {
            ProductDTO productDTO = productClient.getProductById(item.getProductId());

            CartItemWithProductDTO dto = new CartItemWithProductDTO();
            dto.setCartItemId(item.getCartItemId() );
            dto.setProductId(item.getProductId());
            dto.setQuantity(item.getQuantity());
            dto.setProduct(productDTO);

            return dto;
        }).collect(Collectors.toList());
    }
    

}