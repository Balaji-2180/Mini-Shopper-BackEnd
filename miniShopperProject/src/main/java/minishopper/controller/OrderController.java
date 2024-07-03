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
import org.springframework.web.multipart.MultipartFile;

import minishopper.dto.ChangeOrderStatus;
import minishopper.dto.CreateOrderRequestDto;
import minishopper.dto.ExcelOrderDto;
import minishopper.dto.OrderDto;
import minishopper.dto.OrderItemDto;
import minishopper.dto.UpdateOrderItemDto;
import minishopper.entity.OrderItem;
import minishopper.entity.Product;
import minishopper.exception.ResourceNotFoundException;
import minishopper.response.RegisterResponse;
import minishopper.service.OrderService;
import minishopper.service.ProductService;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	OrderService orderService;

	@Autowired
	ProductService productService;

	@PostMapping()
	public ResponseEntity<OrderDto> createOrder(@RequestBody CreateOrderRequestDto orderRequest) {
		OrderDto ordered = orderService.createOrder(orderRequest);
		System.out.println(orderRequest.toString());
		return new ResponseEntity<OrderDto>(ordered, HttpStatus.OK);
	}

	@PostMapping("/singleProduct")
	public ResponseEntity<OrderDto> createOrderForSingleProduct(@RequestBody CreateOrderRequestDto orderRequest) {
		OrderDto ordered = orderService.createOrderSingleProduct(orderRequest);
		return new ResponseEntity<OrderDto>(ordered, HttpStatus.OK);
	}

	@PostMapping("/updateOrderItem")
	public ResponseEntity<OrderDto> updateItemInOrdere(@RequestBody UpdateOrderItemDto updateOrderItem) {
		OrderItemDto updated = orderService.updateOrderItem(updateOrderItem);
		return new ResponseEntity<OrderDto>(new OrderDto(), HttpStatus.OK);
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<List<OrderDto>> getOrderByUserId(@PathVariable String userId) {
		List<OrderDto> orders = orderService.fetchOrderByUser(userId);
		if (orders == null) {
			return new ResponseEntity<List<OrderDto>>(orders, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<OrderDto>>(orders, HttpStatus.OK);
	}

	@GetMapping("/{orderId}")
	public ResponseEntity<OrderDto> getOrderByOrderId(@PathVariable String orderId) {
		OrderDto order = orderService.fetchOrderByOrderId(orderId);
		if (order == null) {
			return new ResponseEntity<OrderDto>(order, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<OrderDto>(order, HttpStatus.OK);
	}

	@DeleteMapping("/item/{orderItemId}")
	public ResponseEntity<String> deleteItem(@PathVariable int orderItemId) {
		OrderItem orderItem = orderService.removeOrderItemByOrderItemId(orderItemId);
		orderService.updateTotalPrice(orderItem);
		return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
	}

	@GetMapping("/excel")
	public ResponseEntity<List<Product>> fetchAllProductsForExcel() {
		System.out.println("in all products excel");
		List<Product> availableProducts = productService.getAllAvailableProduct();
		return new ResponseEntity<List<Product>>(availableProducts, HttpStatus.OK);
	}

	@PostMapping("/excel")
	public ResponseEntity<OrderDto> orderExcelData(@RequestBody CreateOrderRequestDto orderRequest)
			throws ResourceNotFoundException {
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
		} else {
			savedOrder = orderService.createOrderByExcelSheet(orderRequest);
		}
		return new ResponseEntity<OrderDto>(savedOrder, HttpStatus.OK);
	}
	
	
	@PostMapping("/getAllOrders")
	public ResponseEntity<List<OrderDto>> getAllOrdersForShopkeeper(){
		List<OrderDto> orders = orderService.fetchAllOrders();
		System.out.println(orders);
		if(orders.size()>0){
			return new ResponseEntity<List<OrderDto>>(orders, HttpStatus.OK);
		}
		return new ResponseEntity<List<OrderDto>>(orders, HttpStatus.NO_CONTENT);
	}
	
	
	@PostMapping("/changeOrderStatus")
	public ResponseEntity<OrderDto> changeOrderStatus(@RequestBody ChangeOrderStatus changeOrderStatus){
		System.out.println("in chage status controller");
		System.out.println(changeOrderStatus.toString());
		LocalDate localDate = LocalDate.parse(changeOrderStatus.getExpectedDeliveryDate());
		System.out.println(localDate);
//		OrderDto updatedOrder = 
//		ChangeOrderStatus changeOrderStatus=new ChangeOrderStatus();
//		changeOrderStatus.setOrderId("3a9ab3bf-4748-488a-af95-56c4132b307a");
//		changeOrderStatus.setOrderStatus("SENT FOR MODIFICATION");
//		changeOrderStatus.setReason("why you need reason");	
		orderService.updateOrderStatus(changeOrderStatus);
		
		OrderDto updatedOrder = orderService.fetchOrderByOrderId(changeOrderStatus.getOrderId());
		
		//System.out.println(updatedOrder);
		return new ResponseEntity<OrderDto>(updatedOrder, HttpStatus.OK);
	}
	
	

}
