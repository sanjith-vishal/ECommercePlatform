package com.example.demo.dto;

import com.example.demo.model.Order;
import lombok.Data;

import java.util.List;

//@Data
//public class OrderResponseDTO {
//    private Order order;
//    private List<CartItemWithProductDTO> cartItemsWithProducts;
//}

@Data
public class OrderResponseDTO {
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