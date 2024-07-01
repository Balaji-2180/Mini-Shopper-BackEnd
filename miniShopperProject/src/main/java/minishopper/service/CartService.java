package com.example.demo.Service;

import com.example.demo.dtos.AddItemToCart;
import com.example.demo.dtos.CartDto;

public interface CartService {
  CartDto addItemToCart(String userId, AddItemToCart item);
  
  CartDto fetCartbyUser(String userId);
  
  void deleteItemFromCart(String userId,int cartItemId);
}
