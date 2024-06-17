package com.example.demo.Service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Product;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	ProductRepository productRepository;

	@Override
	public List<Product> getAllProducts() {
		//System.out.println(productRepository.findAll());
		return productRepository.findAll();
	}

	@Override
	public Product getByProductId(String productId) {
		return productRepository.findByProductId(productId);
	}

//	@Override
//	public List<Product> getAllProductsByProductName(String productName) {
//		
//		return productRepository.findByProductName(productName);
//	}

//	@Override
//	public List<Product> getAllProductsByBrand(String brand) {
//		
//		return productRepository.findByBrand(brand);
//	}

	@Override
	public List<Product> getAllProductsByCategory(String category) {
		
		return productRepository.findByCategory(category);
	}

	@Override
	public List<Product> getAllAvailableProduct() {
		// TODO Auto-generated method stub
		return productRepository.findAvailableProducts();
	}

	@Override
	public void updateStock(String productId,int stock) {
		// TODO Auto-generated method stub
		productRepository.updateStock(productId,stock);
	}
	
	

}
