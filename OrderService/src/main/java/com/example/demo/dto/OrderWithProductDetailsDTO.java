package com.example.demo.dto;

import lombok.Data;

@Data
public class OrderWithProductDetailsDTO {
	private int orderId;
	private int userId;
	private double totalPrice;
	private String shippingAddress;
	private String orderStatus;
	private String paymentStatus;

	private int productId;
	private int quantity;
	private ProductDTO product;
	private UserDTO user;
}