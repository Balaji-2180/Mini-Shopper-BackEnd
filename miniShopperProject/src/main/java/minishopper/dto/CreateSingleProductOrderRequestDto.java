package minishopper.dto;



import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class CreateSingleProductOrderRequestDto {

	
	@NotBlank(message = "User id should not be null")
	private String userId;
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
	@NotBlank(message = "Product id should not be null")
	private String productId;
	
	@Min(message = "Quantity should be less than 1", value = 1)
	@Max(message = "Quantity should not be greater than 50", value = 50)
	private int quantity;

}
