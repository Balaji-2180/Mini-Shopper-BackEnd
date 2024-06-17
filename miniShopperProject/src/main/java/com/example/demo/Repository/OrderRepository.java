package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.Order;
import com.example.demo.Entity.User;

public interface OrderRepository extends JpaRepository<Order, String>{

	Order findOrderByOrderId(String orderId);
	
	List<Order> findByUser(User user);
}
