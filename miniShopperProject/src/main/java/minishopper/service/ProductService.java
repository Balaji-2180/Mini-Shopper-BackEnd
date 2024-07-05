package minishopper.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import minishopper.entity.Product;

public interface ProductService {

	public List<Product> getAllProducts();

	public Product getByProductId(String productId);

	public List<Product> getAllProductsByCategory(String category);

	public List<Product> getAllAvailableProduct();

	public Product saveImage(MultipartFile image);

}
