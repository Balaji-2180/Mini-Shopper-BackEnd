package com.example.demo.Service;

import java.util.List;

import com.example.demo.Entity.User;

public interface UserService {

	public User saveUser(User user);
	public List<User> getAllUser();
	public User checkUserId(String userId);
}
