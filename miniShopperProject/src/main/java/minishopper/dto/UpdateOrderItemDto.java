package minishopper.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderItemDto {
	
	@NotNull(message = "Order item id should not be null")
	private int orderItemId;
	@NotNull(message = "Quantity should not be null")
	private int quantity;
	@NotNull(message = "Product id should not be null")
	private String productId;

}
