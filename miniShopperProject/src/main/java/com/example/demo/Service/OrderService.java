package com.example.demo.Service;

import java.util.List;

import com.example.demo.dtos.CreateOrderRequest;
import com.example.demo.dtos.OrderDto;

public interface OrderService {
	OrderDto createOrder(CreateOrderRequest orderRequest);
	
	List<OrderDto> fetchOrderByUser(String userId);
	
	OrderDto fetchOrderByOrderId(String orderId);
	
}
