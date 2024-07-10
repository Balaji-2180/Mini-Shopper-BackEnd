package minishopper.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import minishopper.entity.Category;
import minishopper.entity.Product;
import minishopper.exception.ResourceNotFoundException;
import minishopper.service.CategoryService;
import minishopper.service.ProductService;

@Controller
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductService productService;

	@GetMapping
	public ResponseEntity<List<Category>> getAllCategories() {
		List<Category> allCategories = categoryService.fetchAllCategories();
		if(allCategories.size() == 0) {
			return new ResponseEntity<List<Category>>(allCategories, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Category>>(allCategories, HttpStatus.OK);
	}

	@GetMapping("/{categoryId}")
	public ResponseEntity<Category> getCategoryById(@PathVariable String categoryId) throws ResourceNotFoundException{
		Category category = categoryService.fetchCategoryById(categoryId);
		if (category == null) {
			throw new ResourceNotFoundException("Category Not Found");
		}
		return new ResponseEntity<Category>(category, HttpStatus.OK);
	}

	@GetMapping("/{categoryId}/products")
	public ResponseEntity<List<Product>> getProductsByCategoryId(@PathVariable String categoryId) throws ResourceNotFoundException{
		List<Product> categoryProducts = new ArrayList<>();
		Category category = categoryService.fetchCategoryById(categoryId);
		if (category == null) {
			throw new ResourceNotFoundException("Category Not Found");
		}
		String categoryTitle = category.getCategoryTitle().toLowerCase();
		categoryProducts = productService.getAllProductsByCategory(categoryTitle);
		return new ResponseEntity<List<Product>>(categoryProducts, HttpStatus.OK);
	}

}
