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

import jakarta.validation.Valid;
import minishopper.dto.ChangeOrderStatusDto;
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

@Controller
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	OrderService orderService;

	@Autowired
	ProductService productService;

	@PostMapping()
	public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody CreateOrderRequestDto orderRequest) {
	//	System.out.println("in order controller");
		OrderDto ordered = orderService.createOrder(orderRequest);
		return new ResponseEntity<OrderDto>(ordered, HttpStatus.OK);
	}
 
	@PostMapping("/singleProduct")
	public ResponseEntity<OrderDto> createOrderForSingleProduct(@Valid @RequestBody CreateOrderRequestDto orderRequest) {
		OrderDto ordered = orderService.createOrderSingleProduct(orderRequest);
		return new ResponseEntity<OrderDto>(ordered, HttpStatus.OK);
	}

	@PostMapping("/updateOrderItem")
	public ResponseEntity<OrderItemDto> updateItemInOrdere(@Valid @RequestBody UpdateOrderItemDto updateOrderItem) {
		OrderItemDto updatedOrder = orderService.updateOrderItem(updateOrderItem);
		if(updatedOrder == null) {
			return new ResponseEntity<OrderItemDto>(updatedOrder, HttpStatus.NOT_MODIFIED);
		}
		return new ResponseEntity<OrderItemDto>(updatedOrder, HttpStatus.OK);
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
		
		OrderItem item = orderService.getOrderItemById(orderItemId);
		
		if(item == null) {
			return new ResponseEntity<>("Unable to delete item", HttpStatus.NOT_MODIFIED);
		}
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
	public ResponseEntity<OrderDto> orderExcelData(@Valid @RequestBody CreateOrderRequestDto orderRequest)
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
		if(orders.size() == 0){
			return new ResponseEntity<List<OrderDto>>(orders, HttpStatus.NOT_FOUND);			
		}
		return new ResponseEntity<List<OrderDto>>(orders, HttpStatus.OK);
	}
	 
	
	@PostMapping("/changeOrderStatus")
	public ResponseEntity<OrderDto> changeOrderStatusDto(@Valid @RequestBody ChangeOrderStatusDto changeOrderStatusDto){
		System.out.println("in chage status controller");
		System.out.println(changeOrderStatusDto.toString());
		orderService.updateOrderStatus(changeOrderStatusDto);
		OrderDto updatedOrder = orderService.fetchOrderByOrderId(changeOrderStatusDto.getOrderId());
		if(updatedOrder == null) {
			return new ResponseEntity<OrderDto>(updatedOrder, HttpStatus.NOT_MODIFIED);
		}
		return new ResponseEntity<OrderDto>(updatedOrder, HttpStatus.OK);
	}
	
	

}
