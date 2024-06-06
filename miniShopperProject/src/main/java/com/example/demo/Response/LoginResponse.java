package com.example.demo.Response;

import com.example.demo.Entity.User;
import com.example.demo.dtos.UserDto;

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
public class LoginResponse extends BaseResponse{
	
	private UserDto user;
	

}
