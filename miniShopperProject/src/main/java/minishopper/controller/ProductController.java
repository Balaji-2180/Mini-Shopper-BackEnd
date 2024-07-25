
package minishopper.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import minishopper.entity.Product;
import minishopper.exception.ResourceNotFoundException;
import minishopper.service.ProductService;


@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping("/listAllProducts")
	public ResponseEntity<List<Product>> fetchAllProducts() throws ResourceNotFoundException {
		List<Product> allProducts = productService.getAllProducts();
		if (allProducts.size() == 0) {
			throw new ResourceNotFoundException("Products Not Found");
		}
		return new ResponseEntity<List<Product>>(allProducts, HttpStatus.OK);
	}

	@GetMapping("/productId/{productId}")
	public ResponseEntity<Product> fetchSingleProduct(@PathVariable String productId) throws ResourceNotFoundException {
		Product product = productService.getByProductId(productId);
		if (product == null) {
			throw new ResourceNotFoundException("Product Not Found");
		}
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}

}
