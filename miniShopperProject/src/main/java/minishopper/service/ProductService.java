package minishopper.service;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.multipart.MultipartFile;

import minishopper.entity.Product;

public interface ProductService {

	@Cacheable(value = "allProducts")
	public List<Product> getAllProducts();

	@Cacheable(value = "singleProduct", key = "#productId")
	public Product getByProductId(String productId);

	@Cacheable(value = "categoryProducts", key = "#category")
	public List<Product> getAllProductsByCategory(String category);

	public List<Product> getAllAvailableProduct();

	public Product saveImage(MultipartFile image);

}
