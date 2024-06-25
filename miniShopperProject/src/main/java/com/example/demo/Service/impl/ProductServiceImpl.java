package com.example.demo.Service.impl;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
//
//	@Override
//	public void updateStock(String productId,int stock) {
//		// TODO Auto-generated method stub
//		productRepository.updateStock(productId,stock);
//	}

	@Override
	public Product saveImage(MultipartFile image) {
		// TODO Auto-generated method stub
		//System.out.println(productId+"  "+base64+" ");
		Product newProduct=new Product();
		
		try {
			String productId="PID4013";
			
			Product p=productRepository.findByProductId(productId);
			
//			Product p=new Product("PID4044", "ZEBRONICS Zeb-Jaguar Wireless Mouse", "Zebronics", 1920, 299, 76, "Mouse", "ZEBRONICS Zeb-Jaguar Wireless Mouse, 2.4GHz with USB Nano Receiver", 					"");
         	String newImage=Base64.getEncoder().encodeToString(image.getBytes());
         	
         	p.setImage(newImage);
			
		//	System.out.println(p.getImage()+"  "+p.getImage().length());
//			System.out.println(image.getBytes().length);
			
			 newProduct=productRepository.save(p);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	//	p.setProductId("PID4044");
	
		
		
		//return		productRepository.addImage(base64, productId);
		return newProduct;
	}
	
	

}
