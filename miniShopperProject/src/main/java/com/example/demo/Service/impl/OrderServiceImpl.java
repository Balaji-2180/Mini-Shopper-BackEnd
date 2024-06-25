package com.example.demo.Service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import com.example.demo.Repository.OrderItemRepository;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.OrderService;
import com.example.demo.dtos.CreateOrderRequest;
import com.example.demo.dtos.ExcelOrder;
import com.example.demo.dtos.OrderDto;
import com.example.demo.dtos.OrderItemDto;
import com.example.demo.dtos.UpdateOrderItem;
import com.example.demo.exception.ResourceNotFoundException;

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
	OrderItemRepository orderItemRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public OrderDto createOrder(CreateOrderRequest orderRequest) {
		// TODO Auto-generated method stub
		User user = userRepository.findByUserId(orderRequest.getUserId());
		
		Cart cart =  cartRepository.findByCartId(orderRequest.getCartId());
				 
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
		
//		System.out.println("order items in service impl "+order.getOrderItems());
		order.setOrderAmount(totalOrderAmount.get());
	
		Order savedOrder = orderRepository.save(order);

		cart.getItems().clear();
		cartRepository.save(cart);
		
		return modelMapper.map(savedOrder, OrderDto.class);	
	}
	
	
	
	@Override
	public OrderDto createOrderSingleProduct(CreateOrderRequest orderRequest) {
		// TODO Auto-generated method stub
		User user = userRepository.findByUserId(orderRequest.getUserId());
		
		Product product=productRepository.findByProductId(orderRequest.getProductId());
		
		String orderId = UUID.randomUUID().toString();
		String orderNumber = "ORD-" + System.currentTimeMillis() / 1000L + "-" + new Random().nextInt(1000);
		
		Order order = Order.builder().orderId(orderId).orderNumber(orderNumber).orderName(orderRequest.getOrderName())
				.shippingPhone(orderRequest.getShippingPhone()).orderStatus(orderRequest.getOrderStatus())
				.paymentStatus(orderRequest.getPaymentStatus()).shippingAddress(orderRequest.getShippingAddress())
				.city(orderRequest.getCity()).state(orderRequest.getState())
				.postalCode(orderRequest.getPostalCode()).user(user).build();
		
		
		List<OrderItem> orderItems = new ArrayList<>();
		
		OrderItem orderItem = OrderItem.builder().quantity(orderRequest.getQuantity()).product(product)
				.totalPrice(orderRequest.getQuantity() * (product.getDiscountedPrice() != 0 ? product.getDiscountedPrice()
						: product.getUnitPrice()))
				.order(order).build();
		orderItems.add(orderItem);
		
		order.setOrderItems(orderItems);
		order.setOrderAmount(orderItems.get(0).getTotalPrice());
		
		Order savedOrder = orderRepository.save(order);
		
		
		return modelMapper.map(savedOrder, OrderDto.class);
	}
	
	@Override
	public OrderDto createOrderByExcelSheet(CreateOrderRequest orderRequest) throws ResourceNotFoundException{
		// TODO Auto-generated method stub
		User user = userRepository.findByUserId(orderRequest.getUserId());
		

		
		List<ExcelOrder> requestedProducts = orderRequest.getProducts();
		
		List<Product> products=new ArrayList<>();
		
		List<OrderItem> orderable=new ArrayList<>();
		
		

		String orderId = UUID.randomUUID().toString();
		String orderNumber = "ORD-" + System.currentTimeMillis() / 1000L + "-" + new Random().nextInt(1000);
		
		Order order = Order.builder().orderId(orderId).orderNumber(orderNumber).orderName(orderRequest.getOrderName())
				.shippingPhone(orderRequest.getShippingPhone()).orderStatus(orderRequest.getOrderStatus())
				.paymentStatus(orderRequest.getPaymentStatus()).shippingAddress(orderRequest.getShippingAddress())
				.city(orderRequest.getCity()).state(orderRequest.getState())
				.postalCode(orderRequest.getPostalCode()).user(user).build();
		
		
		double totalPrice=0;
		
		for(ExcelOrder requestedProduct : requestedProducts) {
//			System.out.println( requestedProduct.getQuantity());
			if(requestedProduct.getQuantity() > 0 ){
				
			
			int requestedQuantity=requestedProduct.getQuantity();//Integer.parseInt(requestedProduct.getQuantity());
			
			Product product = productRepository.findByProductId(requestedProduct.getProductId());
					//.orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));
			
			//System.out.println("in order service impl  "+product);
			
			if(product==null) {
				throw new ResourceNotFoundException("Product Not Found");
			}else {
				if(product.getStock() < requestedQuantity){
					//return null;  //return out of stock
					throw new ResourceNotFoundException("Product is out of stock");
				}
				products.add(product);		
				OrderItem orderItem = OrderItem.builder().quantity(requestedQuantity).product(product)
						.totalPrice(requestedQuantity * (product.getDiscountedPrice() != 0 ? product.getDiscountedPrice()
								: product.getUnitPrice()))
						.order(order).build();
				orderable.add(orderItem);
				totalPrice+=orderItem.getTotalPrice();				
				System.out.println("order item "+orderItem);
		    }
			}
		}
		System.out.println("after iteration "+orderable.toString() +"  "+totalPrice);
		
		
		
		order.setOrderItems(orderable);
		order.setOrderAmount(totalPrice);
		
		System.out.println(order.toString());
		
		Order savedOrder = orderRepository.save(order);
		
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
		//System.out.println("order items in serivce impl "+order.getOrderItems());
		return modelMapper.map(order, OrderDto.class);
		
	}



	@Override
	public OrderItem removeOrderItemByOrderItemId(int orderItemId) {
		// TODO Auto-generated method stub
		OrderItem deletedItem=orderItemRepository.findById(orderItemId);
		
		orderItemRepository.deleteById(orderItemId);
		
		return deletedItem;
	}



	@Override
	public void updateTotalPrice(OrderItem orderItem) {
		// TODO Auto-generated method stub
		Order order=orderRepository.findOrderByOrderId(orderItem.getOrder().getOrderId());
		order.setOrderAmount(order.getOrderAmount()-orderItem.getTotalPrice());
		System.out.println("updated order amount "+order.getOrderAmount());
		if(order.getOrderAmount()<1) {
			orderRepository.deleteById(order.getOrderId());
		}
		Order updated=orderRepository.save(order);
//		System.out.println(updated.getOrderAmount());
	}



	@Override
	public OrderItemDto updateOrderItem(UpdateOrderItem updateOrderItem) {
		// TODO Auto-generated method stub
		int quantity=updateOrderItem.getQuantity();
		OrderItem orderItem = orderItemRepository.findById(updateOrderItem.getOrderItemId());
		
		orderItem.setQuantity(quantity);
		Product product = productRepository.findByProductId(updateOrderItem.getProductId());
		
		double priceToReduce=orderItem.getTotalPrice();
		
		orderItem.setTotalPrice(quantity * (product.getDiscountedPrice() != 0 ? product.getDiscountedPrice()
				: product.getUnitPrice()));
		OrderItem updatedOrderItem=orderItemRepository.save(orderItem);
		
		Order notUpdatedOrder=orderRepository.findOrderByOrderId(updatedOrderItem.getOrder().getOrderId());
		
		notUpdatedOrder.setOrderAmount((notUpdatedOrder.getOrderAmount()-priceToReduce)+updatedOrderItem.getTotalPrice());
		
		Order updatedOrder=orderRepository.save(notUpdatedOrder);
		return modelMapper.map(updatedOrderItem, OrderItemDto.class);
	}





}
