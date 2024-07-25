package minishopper.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import minishopper.dto.ChangeOrderStatusDto;
import minishopper.dto.CreateExcelOrderRequestDto;
import minishopper.dto.CreateOrderRequestDto;
import minishopper.dto.CreateSingleProductOrderRequestDto;
import minishopper.dto.OrderDto;
import minishopper.dto.OrderItemDto;
import minishopper.dto.UpdateOrderItemDto;
import minishopper.entity.OrderItem;

public interface OrderService {

	@Caching(evict = { 
			@CacheEvict(value = "cart", key = "#orderRequest.userId"),
			@CacheEvict(value = "allOrders", allEntries = true) 
	})
	OrderDto createOrder(CreateOrderRequestDto orderRequest);

	@Caching(evict = {
			@CacheEvict(value = "singleProduct", key = "#orderRequest.productId"), 
			@CacheEvict(value = "allOrders", allEntries = true)
	})
	OrderDto createOrderSingleProduct(CreateSingleProductOrderRequestDto orderRequest);

	@Cacheable(value = "orderByUser", key = "#userId")
	List<OrderDto> fetchOrderByUser(String userId);

	@Cacheable(value = "orderByOrderId", key = "#orderId")
	OrderDto fetchOrderByOrderId(String orderId);

	OrderItem removeOrderItemByOrderItemId(int orderItemId);

	void updateTotalPrice(OrderItem removePrice);

	OrderItemDto updateOrderItem(UpdateOrderItemDto updateOrderItem);

	
	@Caching(evict = {
			@CacheEvict(value = "allProducts"), 
			@CacheEvict(value = "allOrders", allEntries = true)
	})
	OrderDto createOrderByExcelSheet(CreateExcelOrderRequestDto orderRequest);

	@Cacheable(value = "allOrders")
	List<OrderDto> fetchAllOrders();

	@Caching(evict = {
			@CacheEvict(value = "singleProduct", allEntries = true), 
			@CacheEvict(value = "allProducts"), 
			@CacheEvict(value = "categoryProducts", allEntries = true),
			@CacheEvict(value = "allCategories"),
			@CacheEvict(value = "category",  allEntries = true),
			@CacheEvict(value = "orderByUser", allEntries = true),
			@CacheEvict(value = "orderByOrderId", key = "#changeOrderStatusDto.orderId"),
			@CacheEvict(value = "allOrders", allEntries = true)
	})
	void updateOrderStatus(ChangeOrderStatusDto changeOrderStatusDto);

	OrderItem getOrderItemById(int orderItemId);

}
