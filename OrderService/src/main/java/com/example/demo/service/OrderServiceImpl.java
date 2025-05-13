package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CartItemDTO;
import com.example.demo.dto.CartItemWithProductDTO;
import com.example.demo.dto.OrderWithProductDTO;
import com.example.demo.dto.OrderWithProductDetailsDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.ProductWithQuantityDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.exceptions.OrderNotFoundException;
import com.example.demo.feign.ProductClient;
import com.example.demo.feign.ShoppingCartClient;
import com.example.demo.feign.UserClient;
import com.example.demo.model.Order;
import com.example.demo.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

	Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Autowired
	private ShoppingCartClient cartClient;

	@Autowired
	private OrderRepository repository;

	@Autowired
	private ProductClient productClient;

	@Autowired
	private UserClient userClient;

	// Places an order and saves it in the repository.
	// Logs the order placement and returns a confirmation message.
	@Override
	public String placeOrder(Order order) {
		log.info("In OrderServiceImpl placeOrder method....");
		repository.save(order);
		log.info("Order placed successfully with ID: {}", order.getOrderId());
		return "Order placed successfully.";
	}

	// Updates an existing order in the repository.
	// Logs the update operation and returns the updated order object.
	@Override
	public Order updateOrder(Order order) {
		log.info("In OrderServiceImpl updateOrder method....");
		Order updatedOrder = repository.save(order);
		log.info("Order updated with ID: {}", updatedOrder.getOrderId());
		return updatedOrder;
	}

	// Retrieves all orders from the repository.
	// Logs the total number of orders fetched.
	@Override
	public List<Order> getAllOrders() {
		log.info("Fetching all orders...");
		List<Order> orders = repository.findAll();
		log.info("Total orders fetched: {}", orders.size());
		return orders;
	}

	// Deletes an order by its ID if it exists in the repository.
	// Logs the deletion attempt and throws an exception if the order is not found.
	@Override
	public String deleteOrderById(int orderId) {
		log.info("Attempting to delete order with ID: {}", orderId);
		if (repository.existsById(orderId)) {
			repository.deleteById(orderId);
			log.info("Order deleted with ID: {}", orderId);
			return "Order deleted.";
		} else {
			log.warn("Order not found with ID: {}", orderId);
			throw new OrderNotFoundException("Order not found with ID: " + orderId);
		}
	}

	// Retrieves orders filtered by order status.
	// Logs the attempt and number of orders found with the specified status.
	@Override
	public List<Order> getOrdersByOrderStatus(String status) {
		log.info("Fetching orders with status: {}", status);
		List<Order> orders = repository.findByOrderStatus(status);
		log.info("Total orders fetched with status {}: {}", status, orders.size());
		return orders;
	}

	// Retrieves orders filtered by payment status.
	// Logs the attempt and number of orders found with the specified payment
	// status.
	@Override
	public List<Order> getOrdersByPaymentStatus(String status) {
		log.info("Fetching orders with payment status: {}", status);
		List<Order> orders = repository.findByPaymentStatus(status);
		log.info("Total orders fetched with payment status {}: {}", status, orders.size());
		return orders;
	}

	// Places an order for a user based on their cart items.
	// Fetches cart items, calculates total price, and saves the order.
	@Override
	public String placeOrderByUserId(int userId, Order orderDetails) {
		log.info("Placing order for user with ID: {}", userId);
		List<CartItemDTO> cartItems = cartClient.getCartItemsByUserId(userId);

		if (cartItems.isEmpty()) {
			log.warn("Cart is empty for user ID: {}", userId);
			return "Cart is empty. Cannot place order.";
		}

		double totalOrderPrice = cartItems.stream().mapToDouble(CartItemDTO::getTotalPrice).sum();

		Order newOrder = new Order();
		newOrder.setUserId(userId);
		newOrder.setTotalPrice(totalOrderPrice);
		newOrder.setShippingAddress(orderDetails.getShippingAddress());
		newOrder.setOrderStatus(orderDetails.getOrderStatus());
		newOrder.setPaymentStatus(orderDetails.getPaymentStatus());

		repository.save(newOrder);
		log.info("Order placed successfully for user ID: {}. Order ID: {}", userId, newOrder.getOrderId());

		return "Order placed successfully. Order ID: " + newOrder.getOrderId();
	}

	// Retrieves order details along with associated product information.
	// Throws an exception if the order is not found.
	@Override
	public OrderWithProductDTO getOrderWithProductDetails(int orderId) {
		log.info("Fetching order with product details for Order ID: {}", orderId);
		Order order = repository.findById(orderId)
				.orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));

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

		log.info("Fetched order with product details for Order ID: {}", orderId);
		return response;
	}

	// Retrieves orders for a user, including product details.
	// Iterates through orders and cart items to populate DTO objects.
	@Override
	public List<OrderWithProductDetailsDTO> getOrdersByUserId(int userId) {
		log.info("Fetching orders with product details for user ID: {}", userId);
		List<Order> orders = repository.findByUserId(userId);
		if (orders.isEmpty()) {
			throw new OrderNotFoundException("No orders found for user ID: " + userId);
		}
		List<CartItemDTO> cartItems = cartClient.getCartItemsByUserId(userId);

		UserDTO user = userClient.getUser(userId);
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
					dto.setUser(user);

					result.add(dto);
				}
			}
		}

		log.info("Fetched orders with product details for user ID: {}", userId);
		return result;
	}
}