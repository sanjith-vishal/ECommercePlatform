package com.example.demo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Order;
import com.example.demo.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService service;

    @PostMapping("/place")
    public String placeOrder(@Valid @RequestBody Order order) {
        return service.placeOrder(order);
    }

    @PutMapping("/update")
    public Order updateOrder(@Valid @RequestBody Order order) {
        return service.updateOrder(order);
    }

    @GetMapping("/fetchById/{id}")
    public Order getOrder(@PathVariable("id") int id) {
        return service.getOrderById(id);
    }

    @GetMapping("/fetchAll")
    public List<Order> getAllOrders() {
        return service.getAllOrders();
    }

    @DeleteMapping("/delete/{id}")
    public String deleteOrder(@PathVariable("id") int id) {
        return service.deleteOrderById(id);
    }

    @GetMapping("/byUser/{userId}")
    public List<Order> getByUserId(@PathVariable int userId) {
        return service.getOrdersByUserId(userId);
    }

    @GetMapping("/byStatus/{status}")
    public List<Order> getByOrderStatus(@PathVariable String status) {
        return service.getOrdersByOrderStatus(status);
    }

    @GetMapping("/byPaymentStatus/{status}")
    public List<Order> getByPaymentStatus(@PathVariable String status) {
        return service.getOrdersByPaymentStatus(status);
    }
}
