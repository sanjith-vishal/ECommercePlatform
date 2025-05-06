package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_info")
public class Product {

	@Id
	@Min(value = 1, message = "Product ID must be a positive number.")
	private int productId;
	@NotBlank(message = "Product name cannot be blank.")
	private String productName;
	@NotNull(message = "Price is required.")
	@PositiveOrZero(message = "Price cannot be negative.")
	private double price;
	@NotNull(message = "Quantity is required.")
	@PositiveOrZero(message = "Quantity cannot be negative.")
	private int quantity;
}
