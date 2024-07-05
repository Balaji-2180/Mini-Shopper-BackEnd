package minishopper.dto;

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
	private String firstName;
	private String lastName;
	private String shippingAddress;
	private String pinCode;
	private String city;
	private String state;
	private String phoneNumber;
	private LocalDate deliveredDate;
	private Date createdAt;
	private List<OrderItemDto> orderItems = new ArrayList<>();	
	private UserDto user;
	private String reason;
	
	@Override
	public String toString() {
		return "OrderDto [orderId=" + orderId + ", orderNumber=" + orderNumber + ", orderStatus=" + orderStatus
				+ ", paymentStatus=" + paymentStatus + ", orderAmount=" + orderAmount + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", shippingAddress=" + shippingAddress + ", pinCode=" + pinCode
				+ ", city=" + city + ", state=" + state + ", phoneNumber=" + phoneNumber + ", deliveredDate="
				+ deliveredDate + ", createdAt=" + createdAt + ", orderItems=" + orderItems + ", user=" + user + "]";
	}
	
   

}
