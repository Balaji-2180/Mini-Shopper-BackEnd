package com.example.demo.Controller;

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

import com.example.demo.Entity.Cart;
import com.example.demo.Service.CartService;
import com.example.demo.dtos.AddItemToCart;
import com.example.demo.dtos.CartDto;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/carts")
public class CartController {
	 
	@Autowired
	CartService cartService;
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<CartDto> getCartByUserId(@PathVariable String userId){
		//System.out.println("API call from Cart "+userId);
	    CartDto userCart=cartService.fetCartbyUser(userId); 
	    if(userCart==null) {
			return new ResponseEntity<CartDto>(userCart,HttpStatus.NOT_FOUND);
	    }
		return new ResponseEntity<CartDto>(userCart,HttpStatus.OK);
	}
	
	@PostMapping("/{userId}")
	public ResponseEntity<CartDto> addItemToCart(@PathVariable String userId, @RequestBody AddItemToCart item){
		System.out.println("API call from Cart "+userId+"  "+item.getProductId()+"  "+item.getQuantity());
		
	    CartDto c=cartService.addItemToCart(userId, item);
	    
//	    if(c==null) {
//			return new ResponseEntity<CartDto>(c,HttpStatus.NOT_FOUND);
//	    }
	    
	   // System.out.println("in controller "+c.toString());
		return new ResponseEntity<CartDto>(c,HttpStatus.OK);
	}
	
	
	@DeleteMapping("/{userId}/item/{itemId}")
	public void deleteItem(@PathVariable String userId, @PathVariable int itemId) {
		System.out.println("in cart controller delete function ");
		cartService.deleteItemFromCart(userId, itemId);
		//return new ResponseEntity<>("Deleted Successfully",HttpStatus.OK);
	}
	

}
