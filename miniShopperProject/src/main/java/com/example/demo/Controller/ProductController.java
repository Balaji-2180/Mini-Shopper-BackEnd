package com.example.demo.Controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.Entity.Product;
import com.example.demo.Entity.User;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Service.impl.ProductServiceImpl;


@Controller
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	ProductServiceImpl productServiceImpl;
	
	@Autowired
	ProductRepository productRepository;
	
	
	@GetMapping("/listAllProducts")
	public  ResponseEntity<List<Product>>  fetchAllProducts(){
		
		//System.out.println("random UUID "+UUID.randomUUID().toString());
		//System.out.println("getting into all products"+productServiceImpl.getAllProducts());
		System.out.println("getting all products");
		return  new ResponseEntity<List<Product>>(productServiceImpl.getAllProducts(),HttpStatus.OK); 
	}
	
	@PostMapping("/productId/{id}")
	public  ResponseEntity<Product> fetchSingleProduct(@PathVariable String id){
		System.out.println("in single product "+ id);
		return new ResponseEntity<Product>(productServiceImpl.getByProductId(id),HttpStatus.OK);
	}
	
	@PostMapping("/productCategory/{category}")
	public  ResponseEntity<List<Product>> fetchProductsByCategory(@PathVariable String category){
		System.out.println("in single product "+ category);
		return new ResponseEntity<List<Product>>(productServiceImpl.getAllProductsByCategory(category),HttpStatus.OK);
	}
	@GetMapping("/listAvailableProducts")
	public  ResponseEntity<List<Product>>  fetchAvailableProducts(){
		//System.out.println("getting into all products"+productServiceImpl.getAllProducts());
		return  new ResponseEntity<List<Product>>(productServiceImpl.getAllAvailableProduct(),HttpStatus.OK); 
	}
	
	
	@PostMapping("/updateProduct")
	public String reduceProductsCount() {
		
		String productId="PID1040";
		int count=2;
		Product updateProduct=productServiceImpl.getByProductId(productId);
		int updatedStock=(updateProduct.getStock()-count);
		System.out.println("in update "+updateProduct.getStock());
		productServiceImpl.updateStock(productId,updatedStock);
		
		
		
		return "Updated";
	}
	

}
