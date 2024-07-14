
package minishopper.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.poi.util.IOUtils;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import minishopper.entity.Product;
import minishopper.entity.User;
import minishopper.exception.ResourceNotFoundException;
import minishopper.repository.ProductRepository;
import minishopper.service.ProductService;
import minishopper.serviceimpl.ProductServiceImpl;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
//	@GetMapping("/addImage")
//	public String saveImage() {
//		String imagePath="C:\\Users\\2125292\\OneDrive - Cognizant\\Pictures\\ADISA 32LL.png";	
//		byte[] data;
//		try {
//			File file=new File(imagePath);
//			DiskFileItem fileItem=new DiskFileItem("file",	"image/png",false,file.getName(),(int)file.length(),file.getParentFile());			
//			FileInputStream fis= new FileInputStream(file);
//			MultipartFile image= new MockMultipartFile("fileItem",fileItem.getName(),"image/png",IOUtils.toByteArray(fis));
//			String newImage=Base64.getEncoder().encodeToString(image.getBytes());
//			Product p=productService.saveImage(image);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
//		
//		
//		Date date =new Date(0);
//		System.out.println(date.getDate()+"  "+date.getMonth()+"  "+date.getYear());
//		
//		LocalDateTime localDateTime=LocalDateTime.now();
//		
//		String firstHalf=""+localDateTime.getDayOfMonth()+localDateTime.getMonthValue()+localDateTime.getYear()+"-"+
//				localDateTime.getHour()+localDateTime.getMinute();
//		
//		String orderId=firstHalf+"-"+System.currentTimeMillis() / 1000L;
//		
//		String orderNumber= "ORD-" + firstHalf + localDateTime.getSecond();
//		
//		System.out.println(orderId);
//		System.out.println(orderNumber);
		
//		return "image";
//	}
	
	@GetMapping("/listAllProducts")
	public  ResponseEntity<List<Product>>  fetchAllProducts() throws ResourceNotFoundException{	
		List<Product> allProducts = productService.getAllProducts();
		if(allProducts.size()==0) {
			throw new ResourceNotFoundException("Products Not Found");
		}
		return new ResponseEntity<List<Product>>(allProducts,HttpStatus.OK); 
	}
	
	@PostMapping("/productId/{productId}")
	public  ResponseEntity<Product> fetchSingleProduct(@PathVariable String productId) throws ResourceNotFoundException{
		Product product=productService.getByProductId(productId);
		if(product==null) {
			throw new ResourceNotFoundException("Product Not Found");
		}
		return new ResponseEntity<Product>(product,HttpStatus.OK);
	}
	 
//	@PostMapping("/productCategory/{category}")
//	public  ResponseEntity<List<Product>> fetchProductsByCategory(@PathVariable String category){
//		List<Product> categoryProduct=productService.getAllProductsByCategory(category);
//		if(categoryProduct==null) {
//			return new ResponseEntity<List<Product>>(categoryProduct,HttpStatus.NOT_FOUND); 
//		}		
//		return new ResponseEntity<List<Product>>(categoryProduct,HttpStatus.OK);
//	}

}
