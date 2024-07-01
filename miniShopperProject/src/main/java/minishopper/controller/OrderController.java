package com.example.demo.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Entity.OrderItem;
import com.example.demo.Entity.Product;
import com.example.demo.Response.RegisterResponse;
import com.example.demo.Service.OrderService;
import com.example.demo.Service.ProductService;
import com.example.demo.dtos.CreateOrderRequest;
import com.example.demo.dtos.ExcelOrder;
import com.example.demo.dtos.OrderDto;
import com.example.demo.dtos.OrderItemDto;
import com.example.demo.dtos.UpdateOrderItem;
import com.example.demo.exception.ResourceNotFoundException;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/orders")
public class OrderController {
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	ProductService productService;
	
	@PostMapping()
	public ResponseEntity<OrderDto> createOrder(@RequestBody CreateOrderRequest orderRequest){
//		System.out.println("in order Controller"+orderRequest.getCartId()+"  "+orderRequest.getCity()+"  "+
//	orderRequest.getOrderName()+"  "+ orderRequest.getOrderStatus()+"  "+orderRequest.getPaymentStatus()+"  "+
//				orderRequest.getPostalCode()+"  "+orderRequest.getShippingAddress()+"  "+
//				orderRequest.getShippingPhone()+"  "+orderRequest.getState()+"  "+orderRequest.getUserId());
		OrderDto ordered=orderService.createOrder(orderRequest); 
		System.out.println("in create order in controller");

		return new ResponseEntity<OrderDto>(ordered,HttpStatus.OK);
		
	}
	
	@PostMapping("/singleProduct")
	public ResponseEntity<OrderDto> createOrderForSingleProduct(@RequestBody CreateOrderRequest orderRequest){
		System.out.println("in order Controller"+orderRequest.getCartId()+"  "+orderRequest.getCity()+"  "+
	orderRequest.getOrderName()+"  "+ orderRequest.getOrderStatus()+"  "+orderRequest.getPaymentStatus()+"  "+
				orderRequest.getPostalCode()+"  "+orderRequest.getShippingAddress()+"  "+
				orderRequest.getShippingPhone()+"  "+orderRequest.getState()+"  "+orderRequest.getUserId());
		
		OrderDto ordered=orderService.createOrderSingleProduct(orderRequest); 
		
		return new ResponseEntity<OrderDto>(ordered ,HttpStatus.OK);
	} 
	
	@PostMapping("/updateOrderItem")
	public ResponseEntity<OrderDto> updateItemInOrdere(@RequestBody UpdateOrderItem updateOrderItem){
		System.out.println("now working method "+updateOrderItem.getOrderItemId()+"  "+updateOrderItem.getQuantity());
		
		
		OrderItemDto updated=orderService.updateOrderItem(updateOrderItem);
		
		
		return new ResponseEntity<OrderDto>(new OrderDto() ,HttpStatus.OK);
	}
	
	
	
	@GetMapping("/user/{userId}")
	public  ResponseEntity<List<OrderDto>> getOrderByUserId(@PathVariable String userId){
		System.out.println("in order controller get order  ");
		List<OrderDto> orders=orderService.fetchOrderByUser(userId);
		
		if(orders==null) {
			return new ResponseEntity<List<OrderDto>>(orders,HttpStatus.NOT_FOUND);
		} 
		
		
		return new ResponseEntity<List<OrderDto>>(orders,HttpStatus.OK);
	}
	
	@GetMapping("/{orderId}")
	public ResponseEntity<OrderDto> getOrderByOrderId(@PathVariable String orderId){
		
		System.out.println("in order controller get order by order id");
		OrderDto order=orderService.fetchOrderByOrderId(orderId);
		if(order==null) {
			return new ResponseEntity<OrderDto>(order,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<OrderDto>(order,HttpStatus.OK);
	}
	 
	
	@DeleteMapping("/item/{orderItemId}")
	public ResponseEntity<String> deleteItem(@PathVariable int orderItemId) {
		System.out.println("in cart controller delete function "+"  "+orderItemId);
		OrderItem orderItem= orderService.removeOrderItemByOrderItemId(orderItemId);
		orderService.updateTotalPrice(orderItem);
		return new ResponseEntity<>("Deleted Successfully",HttpStatus.OK);
	}
	
	@GetMapping("/excel")
	public  ResponseEntity<List<Product>>  fetchAllProductsForExcel(){
		System.out.println("in all products excel");
		List<Product> availableProducts=productService.getAllAvailableProduct();
		return  new ResponseEntity<List<Product>>(availableProducts,HttpStatus.OK); 
		
	}
	
	
	   
	@PostMapping("/excel")
	public ResponseEntity<OrderDto> readExcelData(@RequestBody CreateOrderRequest orderRequest)  throws  ResourceNotFoundException{

		System.out.println("in excel order controller");
			
			int totalNumberOfProducts=0;
			
			List<ExcelOrder> products = orderRequest.getProducts();
			
			for(ExcelOrder p:products) {
				
				if(p.getQuantity()>0) {
				
					totalNumberOfProducts+=p.getQuantity();
//					System.out.println(totalNumberOfProducts);
				}
				
			}

			OrderDto savedOrder = new OrderDto();
 
			if(totalNumberOfProducts>50) {
				throw  new ResourceNotFoundException("you cannot order more than 50 items in one order");
//				return new ResponseEntity<>("Uploaded Excel sheet contains more than 50 items",HttpStatus.OK);
			}else {
				 savedOrder=orderService.createOrderByExcelSheet(orderRequest);
			}
		
		
		
		return new ResponseEntity<OrderDto>(savedOrder,HttpStatus.OK);
		
//		for(;(row = sheet.getRow(i))!=null;i++) {
//		System.out.println("product  "+row.getCell(0).getStringCellValue());
//		
//		
//		System.out.println("quantity  "+row.getCell(1).getNumericCellValue());
//		
//		String productName=row.getCell(0).getStringCellValue();
//		int quantity= (int) row.getCell(1).getNumericCellValue();
//		
//		products.add(new ExcelOrder(productName,quantity));
//		
//		totalNumberOfProducts += quantity;
//	}
//    orderRequest.setProducts(products);
		
		
		
		
	}
	
	

}
