package com.example.demo.Service;

import java.util.List;

import com.example.demo.Entity.OrderItem;
import com.example.demo.dtos.CreateOrderRequest;
import com.example.demo.dtos.OrderDto;
import com.example.demo.dtos.OrderItemDto;
import com.example.demo.dtos.UpdateOrderItem;

public interface OrderService {
	OrderDto createOrder(CreateOrderRequest orderRequest);
	
    OrderDto createOrderSingleProduct(CreateOrderRequest orderRequest);
	
	List<OrderDto> fetchOrderByUser(String userId);
	
	OrderDto fetchOrderByOrderId(String orderId);
	
	OrderItem removeOrderItemByOrderItemId(int orderItemId);
	
	void updateTotalPrice(OrderItem removePrice);
	
	OrderItemDto updateOrderItem(UpdateOrderItem updateOrderItem);
	
	OrderDto createOrderByExcelSheet(CreateOrderRequest orderRequest);
	
}
