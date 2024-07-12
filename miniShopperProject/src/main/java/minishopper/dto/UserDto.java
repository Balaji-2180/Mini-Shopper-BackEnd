package minishopper.dto;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import minishopper.entity.Address;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

	@NotBlank(message = "User id should not be null")
	private String userId;
	@NotBlank(message = "Password should not be null")
	private String password;
	@NotBlank(message = "First name should not be null")
	private String firstName;
	@NotBlank(message = "Last name should not be null")
	private String lastName;
	@NotBlank(message = "Email should not be null")
	private String email;
	
	@Valid
	private List<AddressDto> address = new ArrayList<>();
	
	@NotBlank(message = "role should not be null")
	private String role;

	@Override
	public String toString() {
		return "UserDto [userId=" + userId + ", password=" + password + ", firstName=" + firstName + ", lastName="
				+ lastName + ", email=" + email + ", address=" + address + ", role=" + role + "]";
	}
	
	
}
