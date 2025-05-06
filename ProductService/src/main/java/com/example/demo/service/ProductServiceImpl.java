package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository repository;

	@Override
	public String saveProduct(Product product) {
		if (repository.save(product) != null) {
			return "Product Saved !!!";
		} else {
			return "Product Not Saved ...";
		}
	}

	@Override
	public Product updateProduct(Product product) {
		return repository.save(product);
	}

	@Override
	public Product getProduct(int productId) {
		Optional<Product> optional = repository.findById(productId);
		return optional.orElse(null);
	}

	@Override
	public List<Product> getAllProducts() {
		return repository.findAll();
	}

	@Override
	public String deleteProduct(int productId) {
		Optional<Product> product = repository.findById(productId);
		if (product.isPresent()) {
			repository.deleteById(productId);
			return "Product Deleted Successfully!";
		} else {
			return "Product Not Found!";
		}
	}

	@Override
	public List<Product> getProductsByCategory(String category) {
		return repository.findByCategory(category);
	}

	@Override
	public List<Product> getProductsByPriceRange(double minPrice, double maxPrice) {
		return repository.findByPriceBetween(minPrice, maxPrice);
	}

}