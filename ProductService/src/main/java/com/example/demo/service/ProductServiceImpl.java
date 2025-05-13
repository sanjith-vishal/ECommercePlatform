package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exceptions.ProductNotFound;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	ProductRepository repository;

	// Saves a product to the repository.
	// Logs the attempt and result of saving the product.
	// Returns a success or failure message based on the save operation.
	@Override
	public String saveProduct(Product product) {
		log.info("Attempting to save product: {}", product.getProductName());
		if (repository.save(product) != null) {
			log.info("Product saved successfully: {}", product.getProductName());
			return "Product Saved !!!";
		} else {
			log.warn("Failed to save product: {}", product.getProductName());
			return "Product Not Saved ...";
		}
	}

	// Updates an existing product in the repository.
	// Logs the product ID being updated.
	// Returns the updated product.
	@Override
	public Product updateProduct(Product product) {
		log.info("Updating product with ID: {}", product.getProductId());
		Product updated = repository.save(product);
		log.info("Product updated: {}", updated.getProductId());
		return updated;
	}

	// Retrieves a product by its ID from the repository.
	// Logs the attempt to fetch the product.
	// Throws an exception if the product is not found.
	@Override
	public Product getProduct(int productId) {
		log.info("Fetching product with ID: {}", productId);
		Optional<Product> optional = repository.findById(productId);
		if (optional.isPresent()) {
			log.info("Product found: {}", productId);
			return optional.get();
		} else {
			log.warn("Product not found: {}", productId);
			throw new ProductNotFound("Product with ID " + productId + " not found");
		}
	}

	// Retrieves all products from the repository.
	// Logs the attempt and number of products retrieved.
	@Override
	public List<Product> getAllProducts() {
		log.info("Fetching all products...");
		List<Product> products = repository.findAll();
		log.info("Total products found: {}", products.size());
		return products;
	}

	// Deletes a product by its ID if it exists in the repository.
	// Logs the attempt and result of the deletion.
	// Returns a success or failure message based on the deletion operation.
	@Override
	public String deleteProduct(int productId) {
		log.info("Attempting to delete product with ID: {}", productId);
		Optional<Product> product = repository.findById(productId);
		if (product.isPresent()) {
			repository.deleteById(productId);
			log.info("Product deleted successfully: {}", productId);
			return "Product Deleted Successfully!";
		} else {
			log.warn("Product not found: {}", productId);
			return "Product Not Found!";
		}
	}

	// Retrieves a list of products filtered by category.
	// Logs the attempt and number of products retrieved.
	@Override
	public List<Product> getProductsByCategory(String category) {
		log.info("Fetching products by category: {}", category);
		List<Product> products = repository.findByCategory(category);
		log.info("Total products found in category {}: {}", category, products.size());
		return products;
	}

	// Retrieves a list of products filtered by price range.
	// Logs the attempt and number of products retrieved.
	@Override
	public List<Product> getProductsByPriceRange(double minPrice, double maxPrice) {
		log.info("Fetching products in price range: {} - {}", minPrice, maxPrice);
		List<Product> products = repository.findByPriceBetween(minPrice, maxPrice);
		log.info("Total products found in price range {} - {}: {}", minPrice, maxPrice, products.size());
		return products;
	}
}