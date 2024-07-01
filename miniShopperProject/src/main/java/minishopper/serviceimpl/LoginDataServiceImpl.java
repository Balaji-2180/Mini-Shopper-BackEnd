package com.example.demo.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.LoginData;
import com.example.demo.Repository.LoginDataRepository;
import com.example.demo.Service.LoginDataService;

@Service
public class LoginDataServiceImpl implements LoginDataService{
	
	@Autowired
	LoginDataRepository loginDataRepo;

	@Override
	public LoginData saveLoginData(LoginData loginData) {
		
		return loginDataRepo.save(loginData);
	}
	

}
