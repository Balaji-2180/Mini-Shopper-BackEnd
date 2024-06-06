package com.example.demo.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name="items")
public class Items {
	@Id
	@Column(name="orderRecord", length=100, nullable=false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String orderRecord;

	@Column(name="orderId", length=100, nullable=false)
	private String orderId;
	
	@Column(name="itemName", length=100, nullable=false)
	private String itemName;
	
	@Column(name="quantity", length=100, nullable=false)
	private String quantity;
	
	@Column(name="price", length=100, nullable=false)
	private String price;

}
