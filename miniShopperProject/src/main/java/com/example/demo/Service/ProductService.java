package com.example.demo.Service;

import java.util.List;

import com.example.demo.Entity.Product;

public interface ProductService {
	
	public List<Product> getAllProducts();
	
	public Product getByProductId(String productId);

//	public List<Product> getAllProductsByProductName(String productName);
	
//	public List<Product> getAllProductsByBrand(String brand);
	
	public List<Product> getAllProductsByCategory(String category);
	
	public List<Product> getAllAvailableProduct();
	
	public void updateStock(String productId,int stock);
	

}
