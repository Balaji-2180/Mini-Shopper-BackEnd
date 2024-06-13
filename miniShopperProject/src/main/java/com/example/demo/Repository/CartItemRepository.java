package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.demo.Entity.CartItem;

public interface CartItemRepository  extends JpaRepository<CartItem, Integer>{
   CartItem findByCartItemId(int cartItemId);
}
