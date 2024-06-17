package com.example.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderRequest {
	
	private String userId;
	private String cartId;
	private String orderStatus;
	private String paymentStatus;
	
	private String orderName;
	
	private String shippingAddress;
	
	private String postalCode;
	
	private String city;
	
	private String state;
	
	private String shippingPhone;
	

}
