package com.example.demo.dtos;

import java.util.List;
import java.util.Map;

import com.example.demo.Entity.Product;

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
	
	private String productId;
	
	private int quantity;
	
	private List<ExcelOrder> products;

	public CreateOrderRequest(String userId, String cartId, String orderStatus, String paymentStatus, String orderName,
			String shippingAddress, String postalCode, String city, String state, String shippingPhone) {
		super();
		this.userId = userId;
		this.cartId = cartId;
		this.orderStatus = orderStatus;
		this.paymentStatus = paymentStatus;
		this.orderName = orderName;
		this.shippingAddress = shippingAddress;
		this.postalCode = postalCode;
		this.city = city;
		this.state = state;
		this.shippingPhone = shippingPhone;
	}

	@Override
	public String toString() {
		return "CreateOrderRequest [userId=" + userId + ", cartId=" + cartId + ", orderStatus=" + orderStatus
				+ ", paymentStatus=" + paymentStatus + ", orderName=" + orderName + ", shippingAddress="
				+ shippingAddress + ", postalCode=" + postalCode + ", city=" + city + ", state=" + state
				+ ", shippingPhone=" + shippingPhone + ", productId=" + productId + ", quantity=" + quantity
				+ ", products=" + products + "]";
	}
	
	
	

}
