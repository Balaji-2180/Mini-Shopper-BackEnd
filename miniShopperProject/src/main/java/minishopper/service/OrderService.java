package minishopper.service;

import java.util.List;

import minishopper.dto.ChangeOrderStatus;
import minishopper.dto.CreateOrderRequestDto;
import minishopper.dto.OrderDto;
import minishopper.dto.OrderItemDto;
import minishopper.dto.UpdateOrderItemDto;
import minishopper.entity.OrderItem;

public interface OrderService {
	OrderDto createOrder(CreateOrderRequestDto orderRequest);

	OrderDto createOrderSingleProduct(CreateOrderRequestDto orderRequest);

	List<OrderDto> fetchOrderByUser(String userId);

	OrderDto fetchOrderByOrderId(String orderId);

	OrderItem removeOrderItemByOrderItemId(int orderItemId);

	void updateTotalPrice(OrderItem removePrice);

	OrderItemDto updateOrderItem(UpdateOrderItemDto updateOrderItem);

	OrderDto createOrderByExcelSheet(CreateOrderRequestDto orderRequest);
	
	List<OrderDto> fetchAllOrders();
	
	void updateOrderStatus(ChangeOrderStatus changeOrderStatus);

}
