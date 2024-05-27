package com.example.demo.Entity;

import jakarta.persistence.Column;

public class Product {
	
	@Column(name="", length=100, nullable=false)
	private String productId;
	
	@Column(name="", length=100, nullable=false)
	private String	productName;

	@Column(name="", length=100, nullable=false)
	private String	brand;

	@Column(name="", length=100, nullable=false)
	private String	unitPrice;

	@Column(name="", length=100, nullable=false)
	private String	stock;

	@Column(name="", length=100, nullable=false)
	private String	category;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Product(String productId, String productName, String brand, String unitPrice, String stock,
			String category) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.brand = brand;
		this.unitPrice = unitPrice;
		this.stock = stock;
		this.category = category;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productName=" + productName + ", brand=" + brand + ", unitPrice="
				+ unitPrice + ", stock=" + stock + ", category=" + category + "]";
	}
	
	
	

}
