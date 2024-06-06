package com.example.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
	
	private String userId;
	private String firstName;
	private String lastName;
	private String email;
	
	private String address;
	private String street;
	private String city;
	private String state;
	private String pinCode;
	private String image;
}
