package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CartItemDTO;
import com.example.demo.dto.CartItemWithProductDTO;
import com.example.demo.dto.OrderResponseDTO;
import com.example.demo.dto.OrderWithProductDTO;
import com.example.demo.dto.OrderWithProductDetailsDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.ProductWithQuantityDTO;
import com.example.demo.feign.ProductClient;
import com.example.demo.feign.ShoppingCartClient;
import com.example.demo.model.Order;
import com.example.demo.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private ShoppingCartClient cartClient;

    @Autowired
    private OrderRepository repository;
    
    @Autowired
    private ProductClient productClient;
    

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

//    @Override
//    public List<Order> getOrdersByUserId(int userId) {
//        return repository.findByUserId(userId);
//    }

    @Override
    public List<Order> getOrdersByOrderStatus(String status) {
        return repository.findByOrderStatus(status);
    }

    @Override
    public List<Order> getOrdersByPaymentStatus(String status) {
        return repository.findByPaymentStatus(status);
    }
    
    @Override
    public String placeOrderByUserId(int userId, Order orderDetails) {
        List<CartItemDTO> cartItems = cartClient.getCartItemsByUserId(userId);

        if (cartItems.isEmpty()) {
            return "Cart is empty. Cannot place order.";
        }

        double totalOrderPrice = cartItems.stream()
                .mapToDouble(CartItemDTO::getTotalPrice)
                .sum();

        Order newOrder = new Order();
        newOrder.setUserId(userId);
        newOrder.setTotalPrice(totalOrderPrice);
        newOrder.setShippingAddress(orderDetails.getShippingAddress());
        newOrder.setOrderStatus(orderDetails.getOrderStatus());
        newOrder.setPaymentStatus(orderDetails.getPaymentStatus());

        repository.save(newOrder);

        return "Order placed successfully. Order ID: " + newOrder.getOrderId();
    }

    @Override
    public OrderWithProductDTO getOrderWithProductDetails(int orderId) {
        Order order = repository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));

        List<CartItemWithProductDTO> cartItems = cartClient.getCartItemsWithProducts(order.getUserId());
        List<ProductWithQuantityDTO> products = new ArrayList<>();

        for (CartItemWithProductDTO item : cartItems) {
            ProductDTO product = item.getProduct();
            ProductWithQuantityDTO dto = new ProductWithQuantityDTO();

            dto.setProductId(product.getProductId());
            dto.setName(product.getProductName());
            dto.setDescription(product.getDescription());
            dto.setPrice(product.getPrice());
            dto.setOrderedquantity(item.getQuantity());

            products.add(dto);
        }

        OrderWithProductDTO response = new OrderWithProductDTO();
        response.setOrder(order);
        response.setProducts(products);

        return response;
    }
    
    @Override
    public List<OrderWithProductDetailsDTO> getOrdersByUserId(int userId) {
        List<Order> orders = repository.findByUserId(userId);
        List<CartItemDTO> cartItems = cartClient.getCartItemsByUserId(userId);

        List<OrderWithProductDetailsDTO> result = new ArrayList<>();

        for (Order order : orders) {
            for (CartItemDTO cartItem : cartItems) {
                if (cartItem.getUserId() == userId) {
                    ProductDTO product = productClient.getProductById(cartItem.getProductId());

                    OrderWithProductDetailsDTO dto = new OrderWithProductDetailsDTO();
                    dto.setOrderId(order.getOrderId());
                    dto.setUserId(order.getUserId());
                    dto.setTotalPrice(order.getTotalPrice());
                    dto.setShippingAddress(order.getShippingAddress());
                    dto.setOrderStatus(order.getOrderStatus());
                    dto.setPaymentStatus(order.getPaymentStatus());
                    dto.setProductId(cartItem.getProductId());
                    dto.setQuantity(cartItem.getQuantity());
                    dto.setProduct(product);

                    result.add(dto);
                }
            }
        }

        return result;
    }
    
}
