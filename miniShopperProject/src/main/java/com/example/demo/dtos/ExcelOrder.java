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
	private String productName;
	private String quantity;
	@Override
	public String toString() {
		return "ExcelOrder [productName=" + productName + ", quantity=" + quantity + "]";
	}
	


}
