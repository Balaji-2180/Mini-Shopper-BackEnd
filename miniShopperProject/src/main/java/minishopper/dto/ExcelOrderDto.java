package minishopper.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExcelOrderDto {
    
	@NotBlank(message = "Product id should not be null")
	private String productId;
	
	@NotBlank(message = "Product name should not be null")
	private String	productName;
	
	@NotBlank(message = "Brand should not be null")
	private String	brand;
	
    @Min(message = "Unit price should be less than 0", value = 0)
    private double	unitPrice;
    
    @Min(message = "Discounted price should be less than 0", value = 0)	
    private double	discountedPrice;
    
    @Min(message = "Stock should be less than 0", value = 0)
	private int	stock;
    
	@NotBlank(message = "Category should not be null")
	private String	category;
	
	@NotBlank(message = "Short Description should not be null")
	private String	shortDescription;
	
	@Min(message = "Quantity should be less than 0", value = 0)
	@Max(message = "Quantity should not be greater than 50", value = 50)
	private int quantity;

	@Override
	public String toString() {
		return "ExcelOrder [productId=" + productId + ", productName=" + productName + ", brand=" + brand
				+ ", unitPrice=" + unitPrice + ", discountedPrice=" + discountedPrice + ", stock=" + stock
				+ ", category=" + category + ", shortDescription=" + shortDescription + ", quantity=" + quantity + "]";
	}
	
	


}
