package minishopper.dto;

import java.util.Date;

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
public class ChangeOrderStatus {

	private String orderId;
	private String orderStatus;
	private String reason;
	private String expectedDeliveryDate;
	@Override
	public String toString() {
		return "ChangeOrderStatus [orderId=" + orderId + ", orderStatus=" + orderStatus + ", reason=" + reason
				+ ", expectedDeliveryDate=" + expectedDeliveryDate + "]";
	}
	
	
}


