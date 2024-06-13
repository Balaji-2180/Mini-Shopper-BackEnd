package com.example.demo.dtos;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
	private String cartId;
	
	private UserDto userDto;
	
	private List<CartItemDto> items=new ArrayList<>();

}
