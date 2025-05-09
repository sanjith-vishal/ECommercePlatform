package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CartItemDTO;
import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.feign.OrderClient;
import com.example.demo.feign.ProductClient;
import com.example.demo.feign.ShoppingCartClient;
import com.example.demo.feign.UserClient;
import com.example.demo.model.Admin;
import com.example.demo.repository.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired 
	private UserClient userClient;
	
    @Autowired 
    private ProductClient productClient;
    
    @Autowired 
    private OrderClient orderClient;
    
    @Autowired 
    private ShoppingCartClient cartClient;
	
    @Autowired
    AdminRepository repository;

    @Override
    public String saveAdmin(Admin admin) {
        repository.save(admin);
        return "Admin saved successfully.";
    }

    @Override
    public Admin updateAdmin(Admin admin) {
        return repository.save(admin);
    }

    @Override
    public Admin getAdminById(int id) {
        Optional<Admin> optional = repository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public List<Admin> getAllAdmins() {
        return repository.findAll();
    }

    @Override
    public String deleteAdminById(int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return "Admin deleted successfully.";
        } else {
            return "Admin not found.";
        }
    }
    
    @Override
    public List<UserDTO> fetchAllUsers() {
        return userClient.getAllUsers();
    }

    @Override
    public List<ProductDTO> fetchAllProducts() {
        return productClient.getAllProducts();
    }

    @Override
    public List<OrderDTO> fetchAllOrders() {
        return orderClient.getAllOrders();
    }

    @Override
    public List<CartItemDTO> fetchAllCartItems() {
        return cartClient.getAllCartItems();
    }

    @Override
    public String deleteUser(int id) {
        return userClient.deleteUser(id);
    }

    @Override
    public String deleteProduct(int id) {
        return productClient.deleteProduct(id);
    }

    @Override
    public String deleteOrder(int id) {
        return orderClient.deleteOrder(id);
    }

    @Override
    public String deleteCartItem(int id) {
        return cartClient.deleteCartItem(id);
    }
}
