package com.example.demo.Entity;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
	
	@Id
	private String orderId;
	
	@Column(nullable=false)
	private String orderNumber;
	
	@Column(nullable = false, columnDefinition = "varchar(20) default 'PENDING'")
	private String orderStatus;
	
	@Column(nullable = false, columnDefinition = "varchar(20) default 'NOT PAID'")
	private String paymentStatus;
	
	@Column(nullable = false)
	private double orderAmount;
	
	@Column(nullable = false)
	private String orderName;
	
	@Column(nullable = false)
	private String shippingAddress;
	
	@Column(length = 6, nullable = false)
	private String postalCode;
	
	@Column(nullable = false)
	private String city;
	
	@Column(nullable = false)
	private String state;
	
	@Column(length=10,nullable = false)
	private String shippingPhone;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id",nullable = false)
	private User user;
	
	@Column(nullable = true)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate deliveredDate;
	
	
	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private Date createdAt;
	
	@OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<OrderItem> orderItems=new ArrayList<>();

	public Order(String orderId, String orderNumber, String orderStatus, String paymentStatus, double orderAmount,
			String orderName, String shippingAddress, String postalCode, String city, String state,
			String shippingPhone, User user, LocalDate deliveredDate, Date createdAt) {
		super();
		this.orderId = orderId;
		this.orderNumber = orderNumber;
		this.orderStatus = orderStatus;
		this.paymentStatus = paymentStatus;
		this.orderAmount = orderAmount;
		this.orderName = orderName;
		this.shippingAddress = shippingAddress;
		this.postalCode = postalCode;
		this.city = city;
		this.state = state;
		this.shippingPhone = shippingPhone;
		this.user = user;
		this.deliveredDate = deliveredDate;
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", orderNumber=" + orderNumber + ", orderStatus=" + orderStatus
				+ ", paymentStatus=" + paymentStatus + ", orderAmount=" + orderAmount + ", orderName=" + orderName
				+ ", shippingAddress=" + shippingAddress + ", postalCode=" + postalCode + ", city=" + city + ", state="
				+ state + ", shippingPhone=" + shippingPhone + ", user=" + user + ", deliveredDate=" + deliveredDate
				+ ", createdAt=" + createdAt + ", orderItems=" + orderItems + "]";
	}
	
	

}
