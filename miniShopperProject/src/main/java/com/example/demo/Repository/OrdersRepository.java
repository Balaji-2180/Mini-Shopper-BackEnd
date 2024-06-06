package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.Orders;





public interface OrdersRepository extends JpaRepository<Orders, String>{
	

}
