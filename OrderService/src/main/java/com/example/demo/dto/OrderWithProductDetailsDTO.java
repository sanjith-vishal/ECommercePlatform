package com.example.demo.dto;

import com.example.demo.dto.ProductDTO;
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
}