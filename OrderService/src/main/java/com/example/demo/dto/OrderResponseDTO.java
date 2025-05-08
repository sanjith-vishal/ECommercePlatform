package com.example.demo.dto;

import com.example.demo.model.Order;
import lombok.Data;

import java.util.List;

@Data
public class OrderResponseDTO {
    private Order order;
    private List<CartItemWithProductDTO> cartItemsWithProducts;
}
