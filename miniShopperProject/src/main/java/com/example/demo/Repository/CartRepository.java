package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.Cart;
import com.example.demo.Entity.User;


public interface CartRepository  extends JpaRepository<Cart, String>{
	
	Cart findByUser(User user);
	Cart findByCartId(String cartId);

}
