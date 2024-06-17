package com.example.demo.Service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Cart;
import com.example.demo.Entity.CartItem;
import com.example.demo.Entity.Order;
import com.example.demo.Entity.OrderItem;
import com.example.demo.Entity.Product;
import com.example.demo.Entity.User;
import com.example.demo.Repository.CartRepository;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.OrderService;
import com.example.demo.dtos.CreateOrderRequest;
import com.example.demo.dtos.OrderDto;

@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public OrderDto createOrder(CreateOrderRequest orderRequest) {
		// TODO Auto-generated method stub
		User user = userRepository.findByUserId(orderRequest.getUserId());
		
		Cart cart = this.cartRepository.findByCartId(orderRequest.getCartId());
				
		List<CartItem> cartItems = cart.getItems();
		
		
		String orderId = UUID.randomUUID().toString();
		String orderNumber = "ORD-" + System.currentTimeMillis() / 1000L + "-" + new Random().nextInt(1000);
		AtomicReference<Double> totalOrderAmount = new AtomicReference<Double>((double) 0);
		
		Order order = Order.builder().orderId(orderId).orderNumber(orderNumber).orderName(orderRequest.getOrderName())
				.shippingPhone(orderRequest.getShippingPhone()).orderStatus(orderRequest.getOrderStatus())
				.paymentStatus(orderRequest.getPaymentStatus()).shippingAddress(orderRequest.getShippingAddress())
				.city(orderRequest.getCity()).state(orderRequest.getState())
				.postalCode(orderRequest.getPostalCode()).user(user).build();
		
		List<OrderItem> orderItems = new ArrayList<>();
		for (CartItem cartItem : cartItems) {
			Product product = cartItem.getProduct();
			int requestedQuantity = cartItem.getQuantity();
			int availableStock = product.getStock();

			if (requestedQuantity > availableStock) {
				continue;
			}

			// cart item -> order item
			OrderItem orderItem = OrderItem.builder().quantity(requestedQuantity).product(product)
					.totalPrice(requestedQuantity * (product.getDiscountedPrice() != 0 ? product.getDiscountedPrice()
							: product.getUnitPrice()))
					.order(order).build();

			totalOrderAmount.set(totalOrderAmount.get() + orderItem.getTotalPrice());
             System.out.println("total order Amount "+totalOrderAmount.get());
	
			product.setStock(availableStock - requestedQuantity);
			productRepository.save(product);

			orderItems.add(orderItem);
		}
		
		
		
		
		order.setOrderItems(orderItems);
		
		System.out.println("order items in service impl "+order.getOrderItems());
		order.setOrderAmount(totalOrderAmount.get());
		
		

		
		Order savedOrder = orderRepository.save(order);

		cart.getItems().clear();
		cartRepository.save(cart);
		
		return modelMapper.map(savedOrder, OrderDto.class);
		
		
	}
	
	public List<OrderDto> fetchOrderByUser(String userId){
		User user=userRepository.findByUserId(userId);
		List<Order> orders=orderRepository.findByUser(user);
		List<OrderDto> orderDto = orders.stream().map(order -> modelMapper.map(order, OrderDto.class))
				.collect(Collectors.toList());
		return orderDto;
	}
	
	
	public OrderDto fetchOrderByOrderId(String orderId) {
		
		Order order=orderRepository.findOrderByOrderId(orderId);	
		System.out.println("order items in serivce impl "+order.getOrderItems());
		return modelMapper.map(order, OrderDto.class);
		
	}
	

}
