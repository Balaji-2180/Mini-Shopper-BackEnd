package minishopper.controller;

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

import minishopper.dto.AddItemToCartDto;
import minishopper.dto.CartDto;
import minishopper.entity.CartItem;
import minishopper.service.CartService;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/carts")
public class CartController {

	@Autowired
	CartService cartService;

	@GetMapping("/user/{userId}")
	public ResponseEntity<CartDto> getCartByUserId(@PathVariable String userId) {
		CartDto userCart = cartService.fetCartbyUser(userId);
		if (userCart == null) {
			return new ResponseEntity<CartDto>(userCart, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<CartDto>(userCart, HttpStatus.OK);
	}

	@PostMapping("/{userId}")
	public ResponseEntity<CartDto> addItemToCart(@PathVariable String userId, @RequestBody AddItemToCartDto item) {
		CartDto cartDto = cartService.addItemToCart(userId, item);
		if(cartDto == null) {
			return new ResponseEntity<CartDto>(cartDto, HttpStatus.NOT_MODIFIED);
		}
		return new ResponseEntity<CartDto>(cartDto, HttpStatus.OK);
	}

	@DeleteMapping("/{userId}/item/{itemId}")
	public ResponseEntity<String> deleteItem(@PathVariable String userId, @PathVariable int itemId) {
		cartService.deleteItemFromCart(userId, itemId);
		CartItem cartItem = cartService.getCartItemById(itemId);
		if(cartItem != null) {
			return new ResponseEntity<String>("Unable to Delete Item", HttpStatus.NOT_MODIFIED);
		}
		return new ResponseEntity<String>("Deleted Successfully", HttpStatus.OK);
	}

}
