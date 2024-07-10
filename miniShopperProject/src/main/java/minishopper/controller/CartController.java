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

import jakarta.validation.Valid;
import minishopper.dto.AddItemToCartDto;
import minishopper.dto.CartDto;
import minishopper.entity.CartItem;
import minishopper.entity.User;
import minishopper.exception.InvalidInputException;
import minishopper.exception.ResourceNotFoundException;
import minishopper.exception.UnauthorizedException;
import minishopper.service.CartService;
import minishopper.service.UserService;

@Controller
@RequestMapping("/carts")
public class CartController {

	@Autowired
	private CartService cartService;
	
	@Autowired
	private UserService userService;
	
	public boolean checkUserId(String userId) {
		User loginUser = userService.checkUserId(userId);
		if(loginUser == null) {
			return false;
		}
		return true;
	}

	@PostMapping("/user/{userId}")
	public ResponseEntity<CartDto> getCartByUserId(@PathVariable String userId) throws InvalidInputException{
		if(!checkUserId(userId)) {
			throw new InvalidInputException("Invalid UserId !");
		}
		CartDto userCart = cartService.fetCartbyUser(userId);
		if (userCart == null) {
			return new ResponseEntity<CartDto>(userCart, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<CartDto>(userCart, HttpStatus.OK);
	}

	@PostMapping("/{userId}")
	public ResponseEntity<CartDto> addItemToCart(@PathVariable String userId, @Valid @RequestBody AddItemToCartDto item) throws InvalidInputException{
		if(!checkUserId(userId)) {
			throw new InvalidInputException("Invalid UserId !");
		}
		CartDto cartDto = cartService.addItemToCart(userId, item);
		if(cartDto == null) {
			return new ResponseEntity<CartDto>(cartDto, HttpStatus.NOT_MODIFIED);
		}
		return new ResponseEntity<CartDto>(cartDto, HttpStatus.OK);
	}

	@DeleteMapping("/{userId}/item/{itemId}")
	public ResponseEntity<String> deleteItem(@PathVariable String userId, @PathVariable int itemId) throws InvalidInputException, ResourceNotFoundException{
		if(!checkUserId(userId)) {
			throw new InvalidInputException("Invalid UserId !");
		}
		if(itemId<1) {
			throw new InvalidInputException("Invalid ItemId !");
		}
		if(cartService.getCartItemById(itemId) == null) {
			throw new ResourceNotFoundException("Cart Item Not Found");
		}
		
		cartService.deleteItemFromCart(userId, itemId);
		CartItem cartItem = cartService.getCartItemById(itemId);
		if(cartItem != null) {
			return new ResponseEntity<String>("Unable to Delete Item", HttpStatus.NOT_MODIFIED);
		}
		return new ResponseEntity<String>("Deleted Successfully", HttpStatus.OK);
	}

}
