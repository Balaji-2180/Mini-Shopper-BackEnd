package minishopper.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import minishopper.dto.ChangeOrderStatusDto;
import minishopper.dto.CreateExcelOrderRequestDto;
import minishopper.dto.CreateOrderRequestDto;
import minishopper.dto.CreateSingleProductOrderRequestDto;
import minishopper.dto.ExcelOrderDto;
import minishopper.dto.OrderDto;
import minishopper.dto.OrderItemDto;
import minishopper.dto.UpdateOrderItemDto;
import minishopper.entity.OrderItem;
import minishopper.entity.Product;
import minishopper.entity.User;
import minishopper.exception.InvalidInputException;
import minishopper.exception.ResourceNotFoundException;
import minishopper.service.OrderService;
import minishopper.service.ProductService;
import minishopper.service.UserService;

@RestController
@RequestMapping("/minishop")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;
	
	public boolean checkNormalUserId(String userId) {
		User loginUser = userService.checkUserId(userId);
		if(loginUser == null) {
			return false;
		}else if(loginUser.getRole().equalsIgnoreCase("user")){
			return true;
		}
		return false;
	}
	
	public boolean checkShopkeeperUserId(String userId) {
		User loginUser = userService.checkUserId(userId);
		if(loginUser == null) {
			return false;
		}else if(loginUser.getRole().equalsIgnoreCase("shopkeeper")){
			return true;
		}
		return false;
	}

	@PostMapping("/placeOrder")
	public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody CreateOrderRequestDto orderRequest) throws InvalidInputException, ResourceNotFoundException{
	//	System.out.println("in order controller");
		if(!checkNormalUserId(orderRequest.getUserId())) {
			throw new InvalidInputException("Invalid UserId !");
		}
		OrderDto ordered = orderService.createOrder(orderRequest);
		return new ResponseEntity<OrderDto>(ordered, HttpStatus.OK);
	}
 
	@PostMapping("/singleProduct")
	public ResponseEntity<OrderDto> createOrderForSingleProduct(@Valid @RequestBody CreateSingleProductOrderRequestDto orderRequest) throws InvalidInputException {
		if(!checkNormalUserId(orderRequest.getUserId())) {
			throw new InvalidInputException("Invalid UserId !");
		} 
		OrderDto ordered = orderService.createOrderSingleProduct(orderRequest);
		return new ResponseEntity<OrderDto>(ordered, HttpStatus.OK);
	}

	@PostMapping("/updateOrderItem")
	public ResponseEntity<OrderItemDto> updateItemInOrdere(@Valid @RequestBody UpdateOrderItemDto updateOrderItem) throws ResourceNotFoundException{
		OrderItemDto updatedOrder = orderService.updateOrderItem(updateOrderItem);
		return new ResponseEntity<OrderItemDto>(updatedOrder, HttpStatus.OK);
	}

	@PostMapping("/user/{userId}")
	public ResponseEntity<List<OrderDto>> getOrderByUserId(@PathVariable String userId) throws InvalidInputException{
		if(!checkNormalUserId(userId)) {
			throw new InvalidInputException("Invalid UserId !");
		}
		List<OrderDto> orders = orderService.fetchOrderByUser(userId);
		if (orders.size() == 0) {
			throw new ResourceNotFoundException("No orders found");
		}
		return new ResponseEntity<List<OrderDto>>(orders, HttpStatus.OK);
	}

	@PostMapping("/{orderId}")
	public ResponseEntity<OrderDto> getOrderByOrderId(@PathVariable String orderId) throws ResourceNotFoundException{
		OrderDto order = orderService.fetchOrderByOrderId(orderId);
		return new ResponseEntity<OrderDto>(order, HttpStatus.OK);
	}

	@DeleteMapping("/item/{orderItemId}")
	public ResponseEntity<String> deleteItem(@PathVariable int orderItemId) throws ResourceNotFoundException{
		OrderItem orderItem = orderService.removeOrderItemByOrderItemId(orderItemId);
		orderService.updateTotalPrice(orderItem);
		
		OrderItem item = orderService.getOrderItemById(orderItemId);
		
		return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
	}

	@GetMapping("/excel")
	public ResponseEntity<List<Product>> fetchAllProductsForExcel() {
		System.out.println("in all products excel");
		List<Product> availableProducts = productService.getAllAvailableProduct();
		if(availableProducts.size() == 0) {
			return new ResponseEntity<List<Product>>(availableProducts, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Product>>(availableProducts, HttpStatus.OK);
	}

	@PostMapping("/excel")
	public ResponseEntity<OrderDto> orderExcelData(@Valid @RequestBody CreateExcelOrderRequestDto orderRequest)
			throws ResourceNotFoundException, InvalidInputException {
		if(!checkNormalUserId(orderRequest.getUserId())) {
			throw new InvalidInputException("Invalid UserId !");
		}
		int totalNumberOfProducts = 0;
		List<ExcelOrderDto> products = orderRequest.getProducts();
		for (ExcelOrderDto p : products) {
			if (p.getQuantity() > 0) { 
				totalNumberOfProducts += p.getQuantity();
			}
		}
		OrderDto savedOrder = new OrderDto();
		if (totalNumberOfProducts > 50) {
			throw new ResourceNotFoundException("you cannot order more than 50 items in one order");
		}else if(totalNumberOfProducts == 0) {
			throw new ResourceNotFoundException("you cannot order less than 1 item in one order");
		}else {
			savedOrder = orderService.createOrderByExcelSheet(orderRequest);
		}
		return new ResponseEntity<OrderDto>(savedOrder, HttpStatus.OK);
	}
	
	
	@PostMapping("/getAllOrders/{userId}")
	public ResponseEntity<List<OrderDto>> getAllOrdersForShopkeeper(@PathVariable String userId)  throws InvalidInputException, ResourceNotFoundException{
		if(!checkShopkeeperUserId(userId)) {
			throw new InvalidInputException("Invalid UserId !");
		}
		List<OrderDto> orders = orderService.fetchAllOrders();
//		List<OrderDto> orders = new ArrayList<>();
		if(orders.size() == 0){
			throw new ResourceNotFoundException("No orders found");		
		}
		return new ResponseEntity<List<OrderDto>>(orders, HttpStatus.OK);
	}
	 
	
	@PostMapping("/changeOrderStatus")
	public ResponseEntity<OrderDto> changeOrderStatusDto(@Valid @RequestBody ChangeOrderStatusDto changeOrderStatusDto) throws InvalidInputException, ResourceNotFoundException{
		System.out.println("in chage status controller");
		if(!checkShopkeeperUserId(changeOrderStatusDto.getUserId())){
			throw new InvalidInputException("Invalid UserId !");
		}
		System.out.println(changeOrderStatusDto.toString());
		orderService.updateOrderStatus(changeOrderStatusDto);
		OrderDto updatedOrder = orderService.fetchOrderByOrderId(changeOrderStatusDto.getOrderId());
		return new ResponseEntity<OrderDto>(updatedOrder, HttpStatus.OK);
	}
	
	

}
