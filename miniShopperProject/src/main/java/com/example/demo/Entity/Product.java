package com.example.demo.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity(name ="product")
@Table(name="product")
public class Product {
	
	@Id
	@Column(name="productId", length=100, nullable=false)
	private String productId;
	
	@Column(name="productName", length=100, nullable=false)
	private String	productName;

	@Column(name="brand", length=100, nullable=false)
	private String	brand;

	@Column(name="unitPrice", length=100, nullable=false)
	private double	unitPrice;
	
	@Column(name="discountedPrice", length=100, nullable=false)
	private double	discountedPrice;

	@Column(name="stock", length=100, nullable=false)
	private int	stock;

	@Column(name="category", length=100, nullable=false)
	private String	category;
	
	@Column(name="shortDescription", length=100, nullable=false)
	private String	shortDescription;

	public double getDiscountedPrice() {
		return discountedPrice;
	}

	public void setDiscountedPrice(double discountedPrice) {
		this.discountedPrice = discountedPrice;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

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

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
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

	public Product(String productId, String productName, String brand, double unitPrice, int stock,
			String category, String	shortDescription,double	discountedPrice) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.brand = brand;
		this.unitPrice = unitPrice;
		this.stock = stock;
		this.category = category;
		this.shortDescription=shortDescription;
		this.discountedPrice = discountedPrice;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productName=" + productName + ", brand=" + brand + ", unitPrice="
				+ unitPrice + ", discountedPrice=" + discountedPrice + ", stock=" + stock + ", category=" + category
				+ ", shortDescription=" + shortDescription + "]";
	}

	
	
	

}
