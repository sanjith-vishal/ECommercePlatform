package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    @NotNull(message = "UserID is required")
    private int userId;

    @Min(value = 0, message = "Total price must be 0 or more")
    private double totalPrice;

    @NotBlank(message = "Shipping address is required")
    private String shippingAddress;

    @NotBlank(message = "Order status is required")
    private String orderStatus;

    @NotBlank(message = "Payment status is required")
    private String paymentStatus;
}