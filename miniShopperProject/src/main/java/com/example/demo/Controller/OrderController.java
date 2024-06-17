package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.Response.RegisterResponse;
import com.example.demo.Service.OrderService;
import com.example.demo.dtos.CreateOrderRequest;
import com.example.demo.dtos.OrderDto;

@Controller
@RequestMapping("/orders")
public class OrderController {
	
	@Autowired
	OrderService orderService;
	
	@PostMapping()
	public ResponseEntity<OrderDto> createOrder(@RequestBody CreateOrderRequest orderRequest){
//		System.out.println("in order Controller"+orderRequest.getCartId()+"  "+orderRequest.getCity()+"  "+
//	orderRequest.getOrderName()+"  "+ orderRequest.getOrderStatus()+"  "+orderRequest.getPaymentStatus()+"  "+
//				orderRequest.getPostalCode()+"  "+orderRequest.getShippingAddress()+"  "+
//				orderRequest.getShippingPhone()+"  "+orderRequest.getState()+"  "+orderRequest.getUserId());
		OrderDto ordered=orderService.createOrder(orderRequest); 
		System.out.println("in create order in controller");

		return new ResponseEntity<OrderDto>(ordered,HttpStatus.OK);
		
	}
	
	@GetMapping("/user/{userId}")
	public  ResponseEntity<List<OrderDto>> getOrderByUserId(@PathVariable String userId){
		System.out.println("in order controller get order  ");
		List<OrderDto> orders=orderService.fetchOrderByUser(userId);
		
		if(orders==null) {
			return new ResponseEntity<List<OrderDto>>(orders,HttpStatus.NOT_FOUND);
		}
		
		
		return new ResponseEntity<List<OrderDto>>(orders,HttpStatus.OK);
	}
	
	@GetMapping("/{orderId}")
	public ResponseEntity<OrderDto> getOrderByOrderId(@PathVariable String orderId){
		
		System.out.println("in order controller get order by order id");
		OrderDto order=orderService.fetchOrderByOrderId(orderId);
		
		
		if(order==null) {
			return new ResponseEntity<OrderDto>(order,HttpStatus.NOT_FOUND);
		}
		
		
		return new ResponseEntity<OrderDto>(order,HttpStatus.OK);
		
	}
	
	
	

}
