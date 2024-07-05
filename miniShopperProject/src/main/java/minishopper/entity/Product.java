package minishopper.entity;

import java.util.Arrays;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
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
	
	@Column(name="shortDescription", length=1000, nullable=false)
	private String	shortDescription;
	
	@Column(name="image", columnDefinition = "longtext")
	private String image;
	
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
				+ ", shortDescription=" + shortDescription + ", image=" + image + "]";
	}

	
	
	

}
