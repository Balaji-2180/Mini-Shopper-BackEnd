package minishopper.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
	
	@Min(message = "Quantity should be less than 1", value = 1)
	private int orderItemId;
	
	@Min(message = "Quantity should be less than 1", value = 1)
	@Max(message = "Quantity should not be greater than 50", value = 50)
	private int quantity;
	
	@NotBlank(message = "Product id should not be null")
	private String productId;

}
