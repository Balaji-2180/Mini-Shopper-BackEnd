package com.example.demo.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
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
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Entity.Product;
import com.example.demo.Entity.User;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Service.ProductService;
import com.example.demo.Service.impl.ProductServiceImpl;


@Controller
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	
	
	@GetMapping("/addImage")
	//public String saveImage(@RequestParam("image") MultipartFile image) {
		public String saveImage() {
		System.out.println("in add image product controller ");
		
		String imagePath="C:\\Users\\2125292\\OneDrive - Cognizant\\Pictures\\Colgate Swarna.png";
		//String imagePath ="C:\\Users\\2125292\\OneDrive - Cognizant\\Pictures\\Fortune sunflower.png";
		
		byte[] data;
		try {
//			data = Files.readAllBytes(Paths.get(imagePath));
//			System.out.println(data);
//			
//			String base64=Base64.getEncoder().encodeToString(data);
//			System.out.println("image "+base64.length()+" "+base64);
//			productService.saveImage(base64, "PID3022");
			File file=new File(imagePath);
			DiskFileItem fileItem=new DiskFileItem("file",	"image/png",false,file.getName(),(int)file.length(),file.getParentFile());
			
			FileInputStream fis= new FileInputStream(file);
			
			MultipartFile image= new MockMultipartFile("fileItem",fileItem.getName(),"image/png",IOUtils.toByteArray(fis));
			
			String newImage=Base64.getEncoder().encodeToString(image.getBytes());
			
			//System.out.println(newImage+"  "+newImage.length());
			
			Product p=productService.saveImage(image);
			//System.out.println(p.toString());
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
		
		return "image";
	}
	
	
	
	
	
	
	@GetMapping("/listAllProducts")
	public  ResponseEntity<List<Product>>  fetchAllProducts(){
		  
		//System.out.println("random UUID "+UUID.randomUUID().toString());
		//System.out.println("getting into all products"+productServiceImpl.getAllProducts());
		System.out.println("getting all products");
//		
//		List<Product> productList=productService.getAllProducts();
//		for(int i=0;i<productList.size();i++) {
//			System.out.println(productList.get(i).toString());
//		}
//		
		return  new ResponseEntity<List<Product>>(productService.getAllProducts(),HttpStatus.OK); 
	}
	
	@PostMapping("/productId/{id}")
	public  ResponseEntity<Product> fetchSingleProduct(@PathVariable String id){
		System.out.println("in single product "+ id);
		Product product=productService.getByProductId(id);
		if(product==null) {
			return new ResponseEntity<Product>(product,HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Product>(product,HttpStatus.OK);
	}
	 
	@PostMapping("/productCategory/{category}")
	public  ResponseEntity<List<Product>> fetchProductsByCategory(@PathVariable String category){
		System.out.println("in single product category"+ category);
		
		List<Product> categoryProduct=productService.getAllProductsByCategory(category);
		if(categoryProduct==null) {
			return new ResponseEntity<List<Product>>(categoryProduct,HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<Product>>(categoryProduct,HttpStatus.OK);
	}
	
//	 @Autowired
//	    ProductRepository productRepository;
//	    
//	  
//		@GetMapping("/checkLogin")
//		@ResponseBody
//		public String checkLogin() {
//			String image="\"C:\\Users\\2125292\\OneDrive - Cognizant\\Pictures\\Screenshots\\classmate_blue_gel.png\"";
//					
//					
//					
//			try {
//				FileInputStream fis=new FileInputStream(image);
//				byte[] imageData = new byte[fis.available()];
//				fis.read(imageData);
//				productRepository.addImage(imageData, "PID1010");
//				
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			return "it is working";
//		}
	
	
	
	
	
//	@GetMapping("/listAvailableProducts")
//	public  ResponseEntity<List<Product>>  fetchAvailableProducts(){
//		//System.out.println("getting into all products"+productServiceImpl.getAllProducts());
//		return  new ResponseEntity<List<Product>>(productService.getAllAvailableProduct(),HttpStatus.OK); 
//	}
//	
//	
//	@PostMapping("/updateProduct")
//	public String reduceProductsCount() {
//		
//		String productId="PID1040";
//		int count=2;
//		Product updateProduct=productService.getByProductId(productId);
//		int updatedStock=(updateProduct.getStock()-count);
//		System.out.println("in update "+updateProduct.getStock());
//		productService.updateStock(productId,updatedStock);
//		
//		
//		
//		return "Updated";
//	}
	

}
