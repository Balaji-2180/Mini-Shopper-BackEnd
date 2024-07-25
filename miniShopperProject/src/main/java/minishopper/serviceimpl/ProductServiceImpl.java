package minishopper.serviceimpl;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import minishopper.entity.Product;
import minishopper.repository.ProductRepository;
import minishopper.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public Product getByProductId(String productId) {
		return productRepository.findByProductId(productId);
	}

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
	public Product saveImage(MultipartFile image) {
		// TODO Auto-generated method stub
		Product newProduct = new Product();
		try {
			String productId = "PID2022";
			Product p = productRepository.findByProductId(productId);
			String newImage = Base64.getEncoder().encodeToString(image.getBytes());
			p.setImage(newImage);
			newProduct = productRepository.save(p);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newProduct;
	}

}
