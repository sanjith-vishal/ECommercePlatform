package com.example.demo.dto;

import lombok.Data;

@Data
public class ProductWithQuantityDTO {
	private int productId;
	private String name;
	private String description;
	private double price;
	private int Orderedquantity;
}