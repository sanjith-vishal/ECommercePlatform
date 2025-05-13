package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CartItemDTO;
import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.exceptions.AdminNotFoundException;
import com.example.demo.feign.OrderClient;
import com.example.demo.feign.ProductClient;
import com.example.demo.feign.ShoppingCartClient;
import com.example.demo.feign.UserClient;
import com.example.demo.model.Admin;
import com.example.demo.repository.AdminRepository;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

	Logger log = LoggerFactory.getLogger(AdminServiceImpl.class);

	private UserClient userClient;
	private ProductClient productClient;
	private OrderClient orderClient;
	private ShoppingCartClient cartClient;
	private AdminRepository repository;

	public AdminServiceImpl(UserClient userClient, ProductClient productClient, OrderClient orderClient,
			ShoppingCartClient cartClient, AdminRepository repository) {
		this.userClient = userClient;
		this.productClient = productClient;
		this.orderClient = orderClient;
		this.cartClient = cartClient;
		this.repository = repository;
	}

	@Override
	public String saveAdmin(Admin admin) {
		log.info("In AdminServiceImpl saveAdmin method....");
		repository.save(admin);
		log.info("Admin saved with ID: {}", admin.getAdminId());
		return "Admin saved successfully.";
	}

	@Override
	public Admin updateAdmin(Admin admin) {
		log.info("In AdminServiceImpl updateAdmin method....");
		Admin updatedAdmin = repository.save(admin);
		log.info("Admin updated with ID: {}", updatedAdmin.getAdminId());
		return updatedAdmin;
	}

	@Override
	public Admin getAdminById(int id) {
		log.info("Fetching admin by ID: {}", id);
		return repository.findById(id).orElseThrow(() -> new AdminNotFoundException("Admin not found with ID: " + id));
	}

	@Override
	public List<Admin> getAllAdmins() {
		log.info("Fetching all admins...");
		List<Admin> admins = repository.findAll();
		log.info("Total admins fetched: {}", admins.size());
		return admins;
	}

	@Override
	public String deleteAdminById(int id) {
		log.info("Deleting admin with ID: {}", id);
		if (!repository.existsById(id)) {
			log.warn("Admin not found with ID: {}", id);
			throw new AdminNotFoundException("Admin not found with ID: " + id);
		}
		repository.deleteById(id);
		log.info("Admin deleted with ID: {}", id);
		return "Admin deleted successfully.";
	}

	@Override
	public List<UserDTO> fetchAllUsers() {
		log.info("Fetching all users...");
		List<UserDTO> users = userClient.getAllUsers();
		log.info("Total users fetched: {}", users.size());
		return users;
	}

	@Override
	public List<ProductDTO> fetchAllProducts() {
		log.info("Fetching all products...");
		List<ProductDTO> products = productClient.getAllProducts();
		log.info("Total products fetched: {}", products.size());
		return products;
	}

	@Override
	public List<OrderDTO> fetchAllOrders() {
		log.info("Fetching all orders...");
		List<OrderDTO> orders = orderClient.getAllOrders();
		log.info("Total orders fetched: {}", orders.size());
		return orders;
	}

	@Override
	public List<CartItemDTO> fetchAllCartItems() {
		log.info("Fetching all cart items...");
		List<CartItemDTO> cartItems = cartClient.getAllCartItems();
		log.info("Total cart items fetched: {}", cartItems.size());
		return cartItems;
	}

	@Override
	public String deleteUser(int id) {
		log.info("Deleting user with ID: {}", id);
		String response = userClient.deleteUser(id);
		log.info("Response from user service for deleting user: {}", response);
		return response;
	}

	@Override
	public String deleteProduct(int id) {
		log.info("Deleting product with ID: {}", id);
		String response = productClient.deleteProduct(id);
		log.info("Response from product service for deleting product: {}", response);
		return response;
	}

	@Override
	public String deleteOrder(int id) {
		log.info("Deleting order with ID: {}", id);
		String response = orderClient.deleteOrder(id);
		log.info("Response from order service for deleting order: {}", response);
		return response;
	}

	@Override
	public String deleteCartItem(int id) {
		log.info("Deleting cart item with ID: {}", id);
		String response = cartClient.deleteCartItem(id);
		log.info("Response from cart service for deleting cart item: {}", response);
		return response;
	}
}