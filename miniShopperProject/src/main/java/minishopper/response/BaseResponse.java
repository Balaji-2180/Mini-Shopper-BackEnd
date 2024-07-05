package minishopper.response;

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
public class BaseResponse {

	private String status;

	private String statusMessage;

	private String message;

	@Override
	public String toString() {
		return "BaseResponse [status=" + status + ", statusMessage=" + statusMessage + ", message=" + message + "]";
	}

}
