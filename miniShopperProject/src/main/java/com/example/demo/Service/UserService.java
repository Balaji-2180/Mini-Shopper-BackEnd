package com.example.demo.Service;

import java.util.List;

import com.example.demo.Entity.User;
import com.example.demo.dtos.UserDto;

public interface UserService {

	public User saveUser(User user);
	
	public User checkUserId(String userId);
	
	public UserDto fetchUserDetailsById(String userId);
	
	public UserDto updateUser(String userId, UserDto userDto);
}
