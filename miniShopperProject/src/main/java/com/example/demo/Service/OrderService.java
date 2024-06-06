package com.example.demo.Service;

import java.util.List;

import com.example.demo.Entity.Orders;

public interface OrderService {
	
	List<Orders> getAllPendingOrders();
	
	List<Orders> getAllOrdersByUserId();
	
	

}
