package minishopper.exception;

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
public class LoginException {
	
	private String status;
	private String message;
	
	@Override
	public String toString() {
		return "LoginException [status=" + status + ", message=" + message + "]";
	}
	

}
