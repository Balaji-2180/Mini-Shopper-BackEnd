package minishopper.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import minishopper.entity.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDto {
	
	private int addressId;

	@NotBlank(message = "Address should not be null")
	private String address;
	
	@NotBlank(message = "Street should not be null")	
	private String street;
	
	@NotBlank(message = "City should not be null")
	private String city;
	
	@NotBlank(message = "State should not be null")
	private String state;
	
	@NotBlank(message = "Pincode should not be null")
	private String pinCode;

}
