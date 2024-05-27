package com.example.demo.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;



@Entity
@Table(name="orders")
public class Orders {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="orderRecord", length=100, nullable=false)
	private String orderRecord;
	
	@Column(name="userId", length=100, nullable=false)
	private String userId;
	
	@Column(name="orderId", length=100, nullable=false)
	private String orderId;
	
	@Column(name="orderName", length=100, nullable=false)
	private String orderName;
	
	@Column(name="placedDate", length=100, nullable=false)
	private LocalDate placedDate;
	
	@Column(name="status", length=30, nullable=false)
	private String status;

	public Orders() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getOrderRecord() {
		return orderRecord;
	}

	public void setOrderRecord(String orderRecord) {
		this.orderRecord = orderRecord;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public LocalDate getPlacedDate() {
		return placedDate;
	}

	public void setPlacedDate(LocalDate placedDate) {
		this.placedDate = placedDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Orders(String orderRecord, String userId, String orderId, String orderName, LocalDate placedDate,
			String status) {
		super();
		this.orderRecord = orderRecord;
		this.userId = userId;
		this.orderId = orderId;
		this.orderName = orderName;
		this.placedDate = placedDate;
		this.status = status;
	}

	@Override
	public String toString() {
		return "Orders [orderRecord=" + orderRecord + ", userId=" + userId + ", orderId=" + orderId + ", orderName="
				+ orderName + ", placedDate=" + placedDate + ", status=" + status + "]";
	}
	
	

}
