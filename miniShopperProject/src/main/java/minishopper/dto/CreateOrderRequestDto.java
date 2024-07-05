package minishopper.dto;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import minishopper.entity.Product;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderRequestDto {
	
	private String userId;
	private String cartId;
	private String orderStatus;
	private String paymentStatus;
	private String orderName;
	private String firstName;
	private String lastName;
	private String shippingAddress;
	private String pinCode;
	private String city;
	private String state;
	private String phoneNumber;
	private String productId;
	private int quantity;
	private List<ExcelOrderDto> products;
	
	public CreateOrderRequestDto(String userId, String cartId, String orderStatus, String paymentStatus, String firstName,String lastName,
			String orderName,String shippingAddress, String pinCode, String city, String state, String phoneNumber) {
		super();
		this.userId = userId;
		this.cartId = cartId;
		this.orderStatus = orderStatus;
		this.paymentStatus = paymentStatus;
		this.firstName = firstName;
		this.lastName = lastName;
		this.orderName = orderName;
		this.shippingAddress = shippingAddress;
		this.pinCode = pinCode;
		this.city = city;
		this.state = state;
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "CreateOrderRequestDto [userId=" + userId + ", cartId=" + cartId + ", orderStatus=" + orderStatus
				+ ", paymentStatus=" + paymentStatus + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", shippingAddress=" + shippingAddress + ", postalCode=" + pinCode + ", city=" + city + ", state="
				+ state + ", phoneNumber=" + phoneNumber + ", productId=" + productId + ", quantity=" + quantity
				+ ", products=" + products + "]";
	}

	public CreateOrderRequestDto(String userId, String cartId, String orderStatus, String paymentStatus,
			String orderName, String shippingAddress, String pinCode, String city, String state, String phoneNumber) {
		super();
		this.userId = userId;
		this.cartId = cartId;
		this.orderStatus = orderStatus;
		this.paymentStatus = paymentStatus;
		this.orderName = orderName;
		this.shippingAddress = shippingAddress;
		this.pinCode = pinCode;
		this.city = city;
		this.state = state;
		this.phoneNumber = phoneNumber;
	}

	

}
