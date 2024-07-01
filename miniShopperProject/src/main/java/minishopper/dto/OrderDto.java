package com.example.demo.dtos;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
public class OrderDto {
	
	private String orderId;
	private String orderNumber;
	private String orderStatus;
	private String paymentStatus;
	private double orderAmount;
	private String orderName;
	private String shippingAddress;
	private String postalCode;
	private String city;
	private String state;
	private String shippingPhone;
	private LocalDate deliveredDate;
	private Date createdAt;
	private List<OrderItemDto> orderItems = new ArrayList<>();	
	private UserDto user;

}
