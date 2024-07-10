package minishopper.dto;

import java.util.List;
import java.util.Map;

import jakarta.validation.constraints.NotNull;
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
	
	@NotNull(message = "User id should not be null")
	private String userId;
	private String cartId;
	@NotNull(message = "Order status should not be null")
	private String orderStatus;
	@NotNull(message = "Payment status should not be null")
	private String paymentStatus;
	@NotNull(message = "Order name should not be null")
	private String orderName;
	@NotNull(message = "First name should not be null")
	private String firstName;
	@NotNull(message = "Last name should not be null")
	private String lastName;
	@NotNull(message = "Shipping address should not be null")
	private String shippingAddress;
	@NotNull(message = "Pincode should not be null")
	private String pinCode;
	@NotNull(message = "City should not be null")
	private String city;
	@NotNull(message = "State should not be null")
	private String state;
	@NotNull(message = "Phone number should not be null")
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
