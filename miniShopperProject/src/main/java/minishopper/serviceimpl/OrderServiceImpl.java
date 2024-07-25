package minishopper.serviceimpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import minishopper.dto.ChangeOrderStatusDto;
import minishopper.dto.CreateExcelOrderRequestDto;
import minishopper.dto.CreateOrderRequestDto;
import minishopper.dto.CreateSingleProductOrderRequestDto;
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
	private OrderRepository orderRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public OrderDto createOrder(CreateOrderRequestDto orderRequest) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		User user = userRepository.findByUserId(orderRequest.getUserId());

		Cart cart = cartRepository.findByCartId(orderRequest.getCartId());

		List<CartItem> cartItems = cart.getItems();
		if (cartItems.size() == 0) {
			throw new ResourceNotFoundException("you cannot order less than 1 item in one order");
		}
		LocalDateTime localDateTime = LocalDateTime.now();

		String firstHalf = "" + localDateTime.getDayOfMonth() + localDateTime.getMonthValue() + localDateTime.getYear()
				+ "-" + localDateTime.getHour() + localDateTime.getMinute();

		String orderId = firstHalf + "-" + System.currentTimeMillis() / 1000L;

		String orderNumber = "ORD-" + firstHalf + localDateTime.getSecond();
		AtomicReference<Double> totalOrderAmount = new AtomicReference<Double>((double) 0);

		Order order = Order.builder().orderId(orderId).orderNumber(orderNumber).orderName(orderRequest.getOrderName())
				.firstName(orderRequest.getFirstName()).lastName(orderRequest.getLastName())
				.phoneNumber(orderRequest.getPhoneNumber()).orderStatus(orderRequest.getOrderStatus())
				.paymentStatus(orderRequest.getPaymentStatus()).shippingAddress(orderRequest.getShippingAddress())
				.city(orderRequest.getCity()).state(orderRequest.getState()).pinCode(orderRequest.getPinCode())
				.user(user).build();

		List<OrderItem> orderItems = new ArrayList<>();
		int totalQuantity = 0;
		for (CartItem cartItem : cartItems) {
			Product product = cartItem.getProduct();
			int requestedQuantity = cartItem.getQuantity();
			int availableStock = product.getStock();
			totalQuantity += requestedQuantity;
			if (totalQuantity > 50) {
				throw new ResourceNotFoundException("you cannot order more than 50 items in one order");
			}

			if (requestedQuantity > availableStock) {
				continue;
			}
			OrderItem orderItem = OrderItem.builder().quantity(requestedQuantity).product(product)
					.totalPrice(requestedQuantity * (product.getDiscountedPrice() != 0 ? product.getDiscountedPrice()
							: product.getUnitPrice()))
					.order(order).build();

			totalOrderAmount.set(totalOrderAmount.get() + orderItem.getTotalPrice());
			orderItems.add(orderItem);
		}
		order.setOrderItems(orderItems);
		order.setOrderAmount(totalOrderAmount.get());
		System.out.println();
		Order savedOrder = orderRepository.save(order);
		cart.getItems().clear();
		cartRepository.save(cart);

		return modelMapper.map(savedOrder, OrderDto.class);
	}

	@Override
	public OrderDto createOrderSingleProduct(CreateSingleProductOrderRequestDto orderRequest) {
		// TODO Auto-generated method stub
		User user = userRepository.findByUserId(orderRequest.getUserId());

		Product product = productRepository.findByProductId(orderRequest.getProductId());
		LocalDateTime localDateTime = LocalDateTime.now();

		String firstHalf = "" + localDateTime.getDayOfMonth() + localDateTime.getMonthValue() + localDateTime.getYear()
				+ "-" + localDateTime.getHour() + localDateTime.getMinute();

		String orderId = firstHalf + "-" + System.currentTimeMillis() / 1000L;

		String orderNumber = "ORD-" + firstHalf + localDateTime.getSecond();

		Order order = Order.builder().orderId(orderId).orderNumber(orderNumber).orderName(orderRequest.getOrderName())
				.firstName(orderRequest.getFirstName()).lastName(orderRequest.getLastName())
				.phoneNumber(orderRequest.getPhoneNumber()).orderStatus(orderRequest.getOrderStatus())
				.paymentStatus(orderRequest.getPaymentStatus()).shippingAddress(orderRequest.getShippingAddress())
				.city(orderRequest.getCity()).state(orderRequest.getState()).pinCode(orderRequest.getPinCode())
				.user(user).build();

		List<OrderItem> orderItems = new ArrayList<>();

		OrderItem orderItem = OrderItem.builder().quantity(orderRequest.getQuantity()).product(product)
				.totalPrice(orderRequest.getQuantity()
						* (product.getDiscountedPrice() != 0 ? product.getDiscountedPrice() : product.getUnitPrice()))
				.order(order).build();
		orderItems.add(orderItem);

		order.setOrderItems(orderItems);
		order.setOrderAmount(orderItems.get(0).getTotalPrice());

		Order savedOrder = orderRepository.save(order);

		return modelMapper.map(savedOrder, OrderDto.class);
	}

	@Override
	public OrderDto createOrderByExcelSheet(CreateExcelOrderRequestDto orderRequest) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		User user = userRepository.findByUserId(orderRequest.getUserId());

		List<ExcelOrderDto> requestedProducts = orderRequest.getProducts();

		List<Product> products = new ArrayList<>();

		List<OrderItem> orderable = new ArrayList<>();

		LocalDateTime localDateTime = LocalDateTime.now();

		String firstHalf = "" + localDateTime.getDayOfMonth() + localDateTime.getMonthValue() + localDateTime.getYear()
				+ "-" + localDateTime.getHour() + localDateTime.getMinute();

		String orderId = firstHalf + "-" + System.currentTimeMillis() / 1000L;

		String orderNumber = "ORD-" + firstHalf + localDateTime.getSecond();

		Order order = Order.builder().orderId(orderId).orderNumber(orderNumber).orderName(orderRequest.getOrderName())
				.firstName(orderRequest.getFirstName()).lastName(orderRequest.getLastName())
				.phoneNumber(orderRequest.getPhoneNumber()).orderStatus(orderRequest.getOrderStatus())
				.paymentStatus(orderRequest.getPaymentStatus()).shippingAddress(orderRequest.getShippingAddress())
				.city(orderRequest.getCity()).state(orderRequest.getState()).pinCode(orderRequest.getPinCode())
				.user(user).build();

		double totalPrice = 0;

		for (ExcelOrderDto requestedProduct : requestedProducts) {
			if (requestedProduct.getQuantity() > 0) {
				int requestedQuantity = requestedProduct.getQuantity();

				Product product = productRepository.findByProductId(requestedProduct.getProductId());

				if (product == null) {
					throw new ResourceNotFoundException("Product Not Found");
				} else {
					if (product.getStock() < requestedQuantity) {
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

	public OrderDto fetchOrderByOrderId(String orderId) throws ResourceNotFoundException {
		Order order = orderRepository.findOrderByOrderId(orderId);
		if (order == null) {
			throw new ResourceNotFoundException("Order not found");
		}
		return modelMapper.map(order, OrderDto.class);
	}

	@Override
	public OrderItem removeOrderItemByOrderItemId(int orderItemId) {
		// TODO Auto-generated method stub
		OrderItem deletedItem = orderItemRepository.findById(orderItemId);
		if (deletedItem == null) {
			throw new ResourceNotFoundException("Order item not found");
		}
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
	public OrderItemDto updateOrderItem(UpdateOrderItemDto updateOrderItem) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		int quantity = updateOrderItem.getQuantity();
		OrderItem orderItem = orderItemRepository.findById(updateOrderItem.getOrderItemId());
		if (orderItem == null) {
			throw new ResourceNotFoundException("Product not found");
		}

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
	public void updateOrderStatus(ChangeOrderStatusDto changeOrderStatusDto) {
		// TODO Auto-generated method stub
		String orderStatus = changeOrderStatusDto.getOrderStatus();
		String orderId = changeOrderStatusDto.getOrderId();
		String reason = changeOrderStatusDto.getReason();
		LocalDate localDate = LocalDate.parse(changeOrderStatusDto.getExpectedDeliveryDate());
		String paymentStatus = "NOT PAID";

		if (orderStatus.equalsIgnoreCase("fulfill")) {
			paymentStatus = "COD";
			Order order = orderRepository.findOrderByOrderId(orderId);
			if (order == null) {
				throw new ResourceNotFoundException("Order not found");
			}
			List<OrderItem> orderItems = order.getOrderItems();
			for (OrderItem orderItem : orderItems) {
				String productId = orderItem.getProduct().getProductId();
				int quantity = orderItem.getQuantity();
				int newQuantity = 0;
				int stock = orderItem.getProduct().getStock();
				if (stock > quantity) {
					newQuantity = stock - quantity;
				}

				productRepository.updateStock(productId, newQuantity);
			}
		}

		orderRepository.updateOrderStatusByOrderId(orderStatus, orderId, reason, localDate, paymentStatus);

	}

	@Override
	public OrderItem getOrderItemById(int orderItemId) {
		// TODO Auto-generated method stub
		OrderItem orderItem = orderItemRepository.findById(orderItemId);
		return orderItem;
	}

}
