package minishopper.dto;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class ChangeOrderStatusDto {
	
	@NotBlank(message = "Order id should not be null")
	private String orderId;
	@NotBlank(message = "Order status should not be null")
	private String orderStatus;
	@NotBlank(message = "Reason should not be null")
	private String reason;
	@NotBlank(message = "Expected delivery date should not be null")
	private String expectedDeliveryDate;
	@NotBlank(message = "User id should not be null")
	private String userId;
    
    
	@Override
	public String toString() {
		return "ChangeOrderStatus [orderId=" + orderId + ", orderStatus=" + orderStatus + ", reason=" + reason
				+ ", expectedDeliveryDate=" + expectedDeliveryDate + "]";
	}
	
	
}


