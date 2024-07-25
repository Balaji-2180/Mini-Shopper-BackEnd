package minishopper.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import minishopper.dto.AddItemToCartDto;
import minishopper.dto.CartDto;
import minishopper.entity.CartItem;

public interface CartService {
	
	@CacheEvict(value = "cart" , key = "#userId")
	CartDto addItemToCart(String userId, AddItemToCartDto item);

	@Cacheable(value = "cart", key = "#userId") 
	CartDto fetCartbyUser(String userId);
        
	@CacheEvict(value = "cart" , key = "#userId")
	void deleteItemFromCart(String userId, int cartItemId);
	
	CartItem getCartItemById(int cartItemId);
	
}
