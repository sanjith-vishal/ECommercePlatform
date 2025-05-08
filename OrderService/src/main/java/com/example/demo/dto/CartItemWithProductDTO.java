package com.example.demo.dto;

import lombok.Data;

@Data
public class CartItemWithProductDTO {
	private int cartItemId;
	private int productId;
	private int quantity;
	private ProductDTO product;
}
