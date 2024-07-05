package minishopper.serviceimpl;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import minishopper.dto.AddItemToCartDto;
import minishopper.dto.CartDto;
import minishopper.entity.Cart;
import minishopper.entity.CartItem;
import minishopper.entity.Product;
import minishopper.entity.User;
import minishopper.repository.CartItemRepository;
import minishopper.repository.CartRepository;
import minishopper.repository.ProductRepository;
import minishopper.repository.UserRepository;
import minishopper.service.CartService;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	CartRepository cartRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	CartItemRepository cartItemRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CartDto addItemToCart(String userId, AddItemToCartDto item) {
		// TODO Auto-generated method stub
		int quantity = item.getQuantity();
		String productId = item.getProductId();
		Product product = productRepository.findByProductId(productId);
		User user = userRepository.findByUserId(userId);
		Cart c = cartRepository.findByUser(user);

		if (c == null) {
			c = new Cart();
			c.setCartId(UUID.randomUUID().toString());
		}
		AtomicReference<Boolean> updated = new AtomicReference<>(false);
		List<CartItem> items = c.getItems();
		items = items.stream().map(i -> {
			if (i.getProduct().getProductId().equals(productId)) {
				i.setQuantity(quantity);
				i.setTotalPrice((product.getDiscountedPrice() == 0) ? quantity * product.getUnitPrice()
						: quantity * product.getDiscountedPrice());
				updated.set(true);
			}
			return i;
		}).collect(Collectors.toList());
		if (!updated.get()) {
			CartItem cartItem = CartItem.builder().quantity(quantity)
					.totalPrice((product.getDiscountedPrice() == 0) ? quantity * product.getUnitPrice()
							: quantity * product.getDiscountedPrice())
					.product(product).cart(c).build();
			c.getItems().add(cartItem);
		}
		c.setUser(user);
		Cart updatedCart = cartRepository.save(c);

		return modelMapper.map(updatedCart, CartDto.class);
	}

	@Override
	public CartDto fetCartbyUser(String userId) {
		User user = userRepository.findByUserId(userId);
		Cart userCart = cartRepository.findByUser(user);

		return modelMapper.map(userCart, CartDto.class);
	}

	@Override
	public void deleteItemFromCart(String userId, int cartItemId) {
		// TODO Auto-generated method stub
		CartItem cartItem = cartItemRepository.findByCartItemId(cartItemId);
		cartItemRepository.delete(cartItem);
	}

	@Override
	public CartItem getCartItemById(int cartItemId) {
		// TODO Auto-generated method stub
		CartItem cartItem = cartItemRepository.findByCartItemId(cartItemId);
		return cartItem;
	}

}
