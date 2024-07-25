package minishopper.dto;



import jakarta.validation.constraints.NotBlank;
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
public class CreateOrderRequestDto {
	
	@NotBlank(message = "User id should not be null")
	private String userId;
	@NotBlank(message = "Cart id should not be null")
	private String cartId;
	@NotBlank(message = "Order status should not be null")
	private String orderStatus;
	@NotBlank(message = "Payment status should not be null")
	private String paymentStatus;
	@NotBlank(message = "Order name should not be null")
	private String orderName;
	@NotBlank(message = "First name should not be null")
	private String firstName;
	@NotBlank(message = "Last name should not be null")
	private String lastName;
	@NotBlank(message = "Shipping address should not be null")
	private String shippingAddress;
	@NotBlank(message = "Pincode should not be null")
	private String pinCode;
	@NotBlank(message = "City should not be null")
	private String city;
	@NotBlank(message = "State should not be null")
	private String state;
	@NotBlank(message = "Phone number should not be null")
	private String phoneNumber;
	


	

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





	@Override
	public String toString() {
		return "CreateOrderRequestDto [userId=" + userId + ", cartId=" + cartId + ", orderStatus=" + orderStatus
				+ ", paymentStatus=" + paymentStatus + ", orderName=" + orderName + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", shippingAddress=" + shippingAddress + ", pinCode=" + pinCode + ", city="
				+ city + ", state=" + state + ", phoneNumber=" + phoneNumber + "]";
	}

	

}
