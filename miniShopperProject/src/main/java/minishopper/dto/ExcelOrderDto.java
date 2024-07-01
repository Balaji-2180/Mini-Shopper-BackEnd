package com.example.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExcelOrder {
private String productId;
	
	private String	productName;

	private String	brand;

    private double	unitPrice;
    
    private double	discountedPrice;

	private int	stock;

	private String	category;
	
	private String	shortDescription;
	
	private int quantity;

	@Override
	public String toString() {
		return "ExcelOrder [productId=" + productId + ", productName=" + productName + ", brand=" + brand
				+ ", unitPrice=" + unitPrice + ", discountedPrice=" + discountedPrice + ", stock=" + stock
				+ ", category=" + category + ", shortDescription=" + shortDescription + ", quantity=" + quantity + "]";
	}
	
	


}
