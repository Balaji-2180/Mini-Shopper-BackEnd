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
public class LoginDto {
    
	@NotBlank(message = "User id should not be null")
	private String UserId;
	@NotBlank(message = "Password should not be null")
	private String password;
	@NotBlank(message = "role should not be null")
	private String role;
}
