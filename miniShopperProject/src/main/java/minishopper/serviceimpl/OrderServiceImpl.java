package minishopper.serviceimpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import minishopper.dto.ChangeOrderStatus;
import minishopper.dto.CreateOrderRequestDto;
import minishopper.dto.ExcelOrderDto;
import minishopper.dto.OrderDto;
import minishopper.dto.OrderItemDto;
import minishopper.dto.UpdateOrderItemDto;
import minishopper.entity.Cart;
import minishopper.entity.CartItem;
import minishopper.entity.Order;
import minishopper.entity.OrderItem;
import minishopper.entity.Product;
import minishopper.entity.User;
import minishopper.exception.ResourceNotFoundException;
import minishopper.repository.CartRepository;
import minishopper.repository.OrderItemRepository;
import minishopper.repository.OrderRepository;
import minishopper.repository.ProductRepository;
import minishopper.repository.UserRepository;
import minishopper.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	OrderItemRepository orderItemRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public OrderDto createOrder(CreateOrderRequestDto orderRequest) {
		// TODO Auto-generated method stub
		User user = userRepository.findByUserId(orderRequest.getUserId());

		Cart cart = cartRepository.findByCartId(orderRequest.getCartId());

		List<CartItem> cartItems = cart.getItems();

//		String orderId = UUID.randomUUID().toString()
		LocalDateTime localDateTime = LocalDateTime.now();

		String firstHalf = "" + localDateTime.getDayOfMonth() + localDateTime.getMonthValue() + localDateTime.getYear()
				+ "-" + localDateTime.getHour() + localDateTime.getMinute();

		String orderId = firstHalf + "-" + System.currentTimeMillis() / 1000L;

		String orderNumber = "ORD-" + firstHalf + localDateTime.getSecond();
//		String orderNumber = "ORD-" + System.currentTimeMillis() / 1000L + "-" + new Random().nextInt(1000);
		AtomicReference<Double> totalOrderAmount = new AtomicReference<Double>((double) 0);

		Order order = Order.builder().orderId(orderId).orderNumber(orderNumber).firstName(orderRequest.getFirstName())
				.lastName(orderRequest.getLastName()).phoneNumber(orderRequest.getPhoneNumber())
				.orderStatus(orderRequest.getOrderStatus()).paymentStatus(orderRequest.getPaymentStatus())
				.shippingAddress(orderRequest.getShippingAddress()).city(orderRequest.getCity())
				.state(orderRequest.getState()).pinCode(orderRequest.getPinCode()).user(user).build();

		List<OrderItem> orderItems = new ArrayList<>();
		for (CartItem cartItem : cartItems) {
			Product product = cartItem.getProduct();
			int requestedQuantity = cartItem.getQuantity();
			int availableStock = product.getStock();

			if (requestedQuantity > availableStock) {
				continue;
			}
			OrderItem orderItem = OrderItem.builder().quantity(requestedQuantity).product(product)
					.totalPrice(requestedQuantity * (product.getDiscountedPrice() != 0 ? product.getDiscountedPrice()
							: product.getUnitPrice()))
					.order(order).build();

			totalOrderAmount.set(totalOrderAmount.get() + orderItem.getTotalPrice());

			product.setStock(availableStock - requestedQuantity);
			productRepository.save(product);
			orderItems.add(orderItem);
		}
		order.setOrderItems(orderItems);
		order.setOrderAmount(totalOrderAmount.get());
		System.out.println();
		Order savedOrder = orderRepository.save(order);
		// System.out.println("saved order "+savedOrder);
		cart.getItems().clear();
		cartRepository.save(cart);

		return modelMapper.map(savedOrder, OrderDto.class);
	}

	@Override
	public OrderDto createOrderSingleProduct(CreateOrderRequestDto orderRequest) {
		// TODO Auto-generated method stub
		User user = userRepository.findByUserId(orderRequest.getUserId());

		Product product = productRepository.findByProductId(orderRequest.getProductId());

//		String orderId = UUID.randomUUID().toString();
//		String orderNumber = "ORD-" + System.currentTimeMillis() / 1000L + "-" + new Random().nextInt(1000);
		LocalDateTime localDateTime = LocalDateTime.now();

		String firstHalf = "" + localDateTime.getDayOfMonth() + localDateTime.getMonthValue() + localDateTime.getYear()
				+ "-" + localDateTime.getHour() + localDateTime.getMinute();

		String orderId = firstHalf + "-" + System.currentTimeMillis() / 1000L;

		String orderNumber = "ORD-" + firstHalf + localDateTime.getSecond();

		Order order = Order.builder().orderId(orderId).orderNumber(orderNumber).firstName(orderRequest.getFirstName())
				.lastName(orderRequest.getLastName()).phoneNumber(orderRequest.getPhoneNumber())
				.orderStatus(orderRequest.getOrderStatus()).paymentStatus(orderRequest.getPaymentStatus())
				.shippingAddress(orderRequest.getShippingAddress()).city(orderRequest.getCity())
				.state(orderRequest.getState()).pinCode(orderRequest.getPinCode()).user(user).build();

		List<OrderItem> orderItems = new ArrayList<>();

		OrderItem orderItem = OrderItem.builder().quantity(orderRequest.getQuantity()).product(product)
				.totalPrice(orderRequest.getQuantity()
						* (product.getDiscountedPrice() != 0 ? product.getDiscountedPrice() : product.getUnitPrice()))
				.order(order).build();
		orderItems.add(orderItem);

		order.setOrderItems(orderItems);
		order.setOrderAmount(orderItems.get(0).getTotalPrice());

		Order savedOrder = orderRepository.save(order);

		product.setStock(product.getStock() - orderRequest.getQuantity());
		productRepository.save(product);

		return modelMapper.map(savedOrder, OrderDto.class);
	}

	@Override
	public OrderDto createOrderByExcelSheet(CreateOrderRequestDto orderRequest) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		User user = userRepository.findByUserId(orderRequest.getUserId());

		List<ExcelOrderDto> requestedProducts = orderRequest.getProducts();

		List<Product> products = new ArrayList<>();

		List<OrderItem> orderable = new ArrayList<>();

//		String orderId = UUID.randomUUID().toString();
//		String orderNumber = "ORD-" + System.currentTimeMillis() / 1000L + "-" + new Random().nextInt(1000);

		LocalDateTime localDateTime = LocalDateTime.now();

		String firstHalf = "" + localDateTime.getDayOfMonth() + localDateTime.getMonthValue() + localDateTime.getYear()
				+ "-" + localDateTime.getHour() + localDateTime.getMinute();

		String orderId = firstHalf + "-" + System.currentTimeMillis() / 1000L;

		String orderNumber = "ORD-" + firstHalf + localDateTime.getSecond();

		Order order = Order.builder().orderId(orderId).orderNumber(orderNumber).firstName(orderRequest.getFirstName())
				.lastName(orderRequest.getLastName()).phoneNumber(orderRequest.getPhoneNumber())
				.orderStatus(orderRequest.getOrderStatus()).paymentStatus(orderRequest.getPaymentStatus())
				.shippingAddress(orderRequest.getShippingAddress()).city(orderRequest.getCity())
				.state(orderRequest.getState()).pinCode(orderRequest.getPinCode()).user(user).build();

		double totalPrice = 0;

		for (ExcelOrderDto requestedProduct : requestedProducts) {
			if (requestedProduct.getQuantity() > 0) {
				int requestedQuantity = requestedProduct.getQuantity();

				Product product = productRepository.findByProductId(requestedProduct.getProductId());

				if (product == null) {
					throw new ResourceNotFoundException("Product Not Found");
				} else {
					if (product.getStock() < requestedQuantity) {
						// return null; //return out of stock
						throw new ResourceNotFoundException("Product is out of stock");
					}
					products.add(product);
					OrderItem orderItem = OrderItem.builder().quantity(requestedQuantity).product(product)
							.totalPrice(requestedQuantity
									* (product.getDiscountedPrice() != 0 ? product.getDiscountedPrice()
											: product.getUnitPrice()))
							.order(order).build();
					orderable.add(orderItem);
					totalPrice += orderItem.getTotalPrice();
					product.setStock(product.getStock() - requestedQuantity);
					productRepository.save(product);
				}
			}
		}
		order.setOrderItems(orderable);
		order.setOrderAmount(totalPrice);
		Order savedOrder = orderRepository.save(order);

		return modelMapper.map(savedOrder, OrderDto.class);
	}

	public List<OrderDto> fetchOrderByUser(String userId) {
		User user = userRepository.findByUserId(userId);
		List<Order> orders = orderRepository.findByUser(user);
		List<OrderDto> orderDto = orders.stream().map(order -> modelMapper.map(order, OrderDto.class))
				.collect(Collectors.toList());
		return orderDto;
	}

	public OrderDto fetchOrderByOrderId(String orderId) {
		Order order = orderRepository.findOrderByOrderId(orderId);
		return modelMapper.map(order, OrderDto.class);
	}

	@Override
	public OrderItem removeOrderItemByOrderItemId(int orderItemId) {
		// TODO Auto-generated method stub
		OrderItem deletedItem = orderItemRepository.findById(orderItemId);
		orderItemRepository.deleteById(orderItemId);
		return deletedItem;
	}

	@Override
	public void updateTotalPrice(OrderItem orderItem) {
		// TODO Auto-generated method stub
		Order order = orderRepository.findOrderByOrderId(orderItem.getOrder().getOrderId());
		order.setOrderAmount(order.getOrderAmount() - orderItem.getTotalPrice());
		if (order.getOrderAmount() < 1) {
			orderRepository.deleteById(order.getOrderId());
		}
		Order updated = orderRepository.save(order);
	}

	@Override
	public OrderItemDto updateOrderItem(UpdateOrderItemDto updateOrderItem) {
		// TODO Auto-generated method stub
		int quantity = updateOrderItem.getQuantity();
		OrderItem orderItem = orderItemRepository.findById(updateOrderItem.getOrderItemId());

		orderItem.setQuantity(quantity);
		Product product = productRepository.findByProductId(updateOrderItem.getProductId());

		double priceToReduce = orderItem.getTotalPrice();

		orderItem.setTotalPrice(
				quantity * (product.getDiscountedPrice() != 0 ? product.getDiscountedPrice() : product.getUnitPrice()));
		OrderItem updatedOrderItem = orderItemRepository.save(orderItem);

		Order notUpdatedOrder = orderRepository.findOrderByOrderId(updatedOrderItem.getOrder().getOrderId());

		notUpdatedOrder
				.setOrderAmount((notUpdatedOrder.getOrderAmount() - priceToReduce) + updatedOrderItem.getTotalPrice());

		Order updatedOrder = orderRepository.save(notUpdatedOrder);
		return modelMapper.map(updatedOrderItem, OrderItemDto.class);
	}

	@Override
	public List<OrderDto> fetchAllOrders() {
		// TODO Auto-generated method stub
		List<Order> allOrders = orderRepository.findAll();
		List<OrderDto> allOrderDto = allOrders.stream().map(order -> modelMapper.map(order, OrderDto.class))
				.collect(Collectors.toList());
		return allOrderDto;
	}

	@Override
	public void updateOrderStatus(ChangeOrderStatus changeOrderStatus) {
		// TODO Auto-generated method stub
		String orderStatus = changeOrderStatus.getOrderStatus();
		String orderId = changeOrderStatus.getOrderId();
		String reason=changeOrderStatus.getReason();
		//String date=changeOrderStatus.getExpectedDeliveryDate();
		LocalDate localDate = LocalDate.parse(changeOrderStatus.getExpectedDeliveryDate());
//		System.out.println(localDate);
//		System.out.println(orderStatus+"  "+orderId);
		
		
		if(orderStatus.equalsIgnoreCase("fulfill")){
			Order order = orderRepository.findOrderByOrderId(orderId);
			List<OrderItem> orderItems = order.getOrderItems();
			for(OrderItem orderItem: orderItems) {
				String productId = orderItem.getProduct().getProductId();
				int quantity = orderItem.getQuantity();
				int newQuantity = 0;
				int stock = orderItem.getProduct().getStock();
				if(stock > quantity){
					newQuantity = stock - quantity;
				}
				
				productRepository.updateStock(productId, newQuantity);
			}
		}
		
		orderRepository.updateOrderStatusByOrderId(orderStatus, orderId, reason, localDate);
		
	} 
 
}
