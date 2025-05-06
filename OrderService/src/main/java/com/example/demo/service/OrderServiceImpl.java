package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Order;
import com.example.demo.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository repository;

    @Override
    public String placeOrder(Order order) {
        repository.save(order);
        return "Order placed successfully.";
    }

    @Override
    public Order updateOrder(Order order) {
        return repository.save(order);
    }

    @Override
    public Order getOrderById(int orderId) {
        Optional<Order> optional = repository.findById(orderId);
        return optional.orElse(null);
    }

    @Override
    public List<Order> getAllOrders() {
        return repository.findAll();
    }

    @Override
    public String deleteOrderById(int orderId) {
        if (repository.existsById(orderId)) {
            repository.deleteById(orderId);
            return "Order deleted.";
        } else {
            return "Order not found.";
        }
    }

    @Override
    public List<Order> getOrdersByUserId(int userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public List<Order> getOrdersByOrderStatus(String status) {
        return repository.findByOrderStatus(status);
    }

    @Override
    public List<Order> getOrdersByPaymentStatus(String status) {
        return repository.findByPaymentStatus(status);
    }
}
