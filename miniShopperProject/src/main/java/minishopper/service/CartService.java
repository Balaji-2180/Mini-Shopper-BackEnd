package minishopper.service;

import minishopper.dto.AddItemToCartDto;
import minishopper.dto.CartDto;
import minishopper.entity.CartItem;

public interface CartService {
	CartDto addItemToCart(String userId, AddItemToCartDto item);

	CartDto fetCartbyUser(String userId);

	void deleteItemFromCart(String userId, int cartItemId);
	
	CartItem getCartItemById(int cartItemId);
	
}
