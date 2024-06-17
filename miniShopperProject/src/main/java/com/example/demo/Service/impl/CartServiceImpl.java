package com.example.demo.Service.impl;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Cart;
import com.example.demo.Entity.CartItem;
import com.example.demo.Entity.Product;
import com.example.demo.Entity.User;
import com.example.demo.Repository.CartItemRepository;
import com.example.demo.Repository.CartRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.dtos.AddItemToCart;
import com.example.demo.Service.CartService;
import com.example.demo.dtos.CartDto;

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
	public CartDto addItemToCart(String userId, AddItemToCart item) {
		// TODO Auto-generated method stub
		int quantity = item.getQuantity();
		String productId = item.getProductId();

		Product product = productRepository.findByProductId(productId);
	//	System.out.println("product ID  "+product.getProductId());
		User user = userRepository.findByUserId(userId);

		Cart c = cartRepository.findByUser(user);
		
		//System.out.println("cart object "+c.getCartId()+"   "+c.getItems().size()+"  "+c.getUser());
		
		if(c==null) {
			
			c= new Cart();
			c.setCartId(UUID.randomUUID().toString());
		}
		AtomicReference<Boolean> updated = new AtomicReference<>(false);
		
		List<CartItem> items=c.getItems();
		
		
		items= items.stream().map(i -> {
			if(i.getProduct().getProductId().equals(productId)) {
				//System.out.println("in stream of items");
			        i.setQuantity(quantity);
				    i.setTotalPrice((product.getDiscountedPrice() == 0) ? quantity * product.getUnitPrice()
						: quantity * product.getDiscountedPrice());
				updated.set(true);
			}
			return i;
		}).collect(Collectors.toList());
		
		if(!updated.get()) {

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
		User user=userRepository.findByUserId(userId);
		
		Cart userCart=cartRepository.findByUser(user);
		
		return modelMapper.map(userCart, CartDto.class);
	}

	@Override
	public void deleteItemFromCart(String userId, int cartItemId) {
		// TODO Auto-generated method stub
		CartItem cartItem=cartItemRepository.findByCartItemId(cartItemId);
		 cartItemRepository.delete(cartItem);
		
		
	}
	
	
	
	
	
	

}
