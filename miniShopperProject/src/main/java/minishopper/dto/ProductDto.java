package com.example.demo.dtos;

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
public class ProductDto {
	
	private String productId;
	
	private String	productName;

	private String	brand;

    private double	unitPrice;
    
    private double	discountedPrice;

	private int	stock;

	private String	category;
	
	private String	shortDescription;

}
