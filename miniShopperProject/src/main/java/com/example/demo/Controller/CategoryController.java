package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.Entity.Category;
import com.example.demo.Entity.Product;
import com.example.demo.Service.CategoryService;
import com.example.demo.Service.ProductService;

@Controller
@RequestMapping("/categories")
public class CategoryController {
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	ProductService productService;
	
	@GetMapping
	public ResponseEntity<List<Category>> getAllCategories(){
		//System.out.println(categoryService.fetchAllCategories());
		
		return new ResponseEntity<List<Category>>(categoryService.fetchAllCategories(), HttpStatus.OK);
	}
	
	@GetMapping("/{categoryId}")
	public ResponseEntity<Category> getCategoryById(@PathVariable String categoryId){
		//System.out.println("category by Id  "+categoryService.fetchCategoryById(categoryId).toString());
		
		return new ResponseEntity<Category>(categoryService.fetchCategoryById(categoryId) , HttpStatus.OK);	
	}
	
	
	@GetMapping("/{categoryId}/products")
	public ResponseEntity<List<Product>> getProductsByCategoryId(@PathVariable String categoryId){
	//	System.out.println("products by category id");
		Category category=categoryService.fetchCategoryById(categoryId);
		String categoryTitle=category.getCategoryTitle().toLowerCase();
		System.out.println(productService.getAllProductsByCategory(categoryTitle));
		return new ResponseEntity<List<Product>>(productService.getAllProductsByCategory(categoryTitle) , HttpStatus.OK);
	}
	
	

}
