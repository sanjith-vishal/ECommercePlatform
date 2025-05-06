package com.example.demo.service;

import java.util.List;
import com.example.demo.model.Order;

public interface OrderService {

    public abstract String placeOrder(Order order);
    public abstract Order updateOrder(Order order);
    public abstract Order getOrderById(int orderId);
    public abstract List<Order> getAllOrders();
    public abstract String deleteOrderById(int orderId);
    public abstract List<Order> getOrdersByUserId(int userId);
    public abstract List<Order> getOrdersByOrderStatus(String status);
    public abstract List<Order> getOrdersByPaymentStatus(String status);
}
