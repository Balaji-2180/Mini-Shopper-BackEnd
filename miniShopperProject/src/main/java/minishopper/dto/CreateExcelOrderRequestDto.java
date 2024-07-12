package minishopper.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class CreateExcelOrderRequestDto {
	
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

	@Valid
	@Size(min=1)
	private List<ExcelOrderDto> products;
}
