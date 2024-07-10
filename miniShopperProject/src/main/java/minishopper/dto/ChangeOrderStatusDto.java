package minishopper.dto;

import java.util.Date;

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
	
    @NotNull(message = "Order id should not be null")
	private String orderId;
    @NotNull(message = "Order status should not be null")
	private String orderStatus;
    @NotNull(message = "Reason should not be null")
	private String reason;
    @NotNull(message = "Expected delivery date should not be null")
	private String expectedDeliveryDate;
    
	@Override
	public String toString() {
		return "ChangeOrderStatus [orderId=" + orderId + ", orderStatus=" + orderStatus + ", reason=" + reason
				+ ", expectedDeliveryDate=" + expectedDeliveryDate + "]";
	}
	
	
}


