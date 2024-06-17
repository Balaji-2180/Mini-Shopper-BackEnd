package com.example.demo.Service.impl;


import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.UserService;
import com.example.demo.dtos.UserDto;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		return userRepository.save(user);
	}

	@Override
	public User checkUserId(String userId) {
		// TODO Auto-generated method stub
		//User user=userRepository.findByUserId(userId);
//		return modelMapper.map(user, UserDto.class);
	    return userRepository.findByUserId(userId);	
	}

	@Override
	public UserDto fetchUserDetailsById(String userId) {
		// TODO Auto-generated method stub
		User user=userRepository.findByUserId(userId);
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public UserDto updateUser(String userId, UserDto userDto) {
		// TODO Auto-generated method stub
		System.out.println("in user service impl");
		User user=userRepository.findByUserId(userId);
		
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setAddress(userDto.getAddress());
		user.setStreet(userDto.getStreet()); 
		user.setCity(userDto.getCity());
		user.setState(userDto.getState());
		user.setPinCode(userDto.getPinCode());
		if(userDto.getImage()!=null) {
			userDto.setImage(userDto.getImage());
		}
		User savedUser=userRepository.save(user);
		
		return modelMapper.map(savedUser, UserDto.class);
	}



}
