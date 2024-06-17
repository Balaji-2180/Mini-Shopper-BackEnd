package com.example.demo.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.Entity.LoginData;
import com.example.demo.Entity.User;
import com.example.demo.Repository.LoginDataRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Response.LoginResponse;
import com.example.demo.Service.LoginDataService;
import com.example.demo.Service.UserService;
import com.example.demo.Service.impl.UserServiceImpl;
import com.example.demo.dtos.LoginDto;
import com.example.demo.dtos.UserDto;
import com.example.demo.exception.LoginException;




@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/users/")
public class LoginController {
    
    @Autowired 
    LoginDataService loginDataService;

    
    @Autowired
    UserService userService;
    
    @Autowired
	private ModelMapper modelMapper;
    
  
	 
	@PostMapping("loginUser")
	public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginDto l) {

		System.out.println(l.toString());
		LocalDate date=LocalDate.now();
		LocalTime time=LocalTime.now();
		
		LoginResponse lr=new LoginResponse();
		lr.setStatus("");
		if(l.getUserId()!=null && l.getPassword()!=null) {

			User loginUser=userService.checkUserId(l.getUserId());
			if(loginUser==null) {
				
				LoginData ld=new LoginData(l.getUserId(),"Invalid UserId and password",date,time);
				loginDataService.saveLoginData(ld);
				lr.setStatus("404");
				lr.setStatusMessage("NOT_FOUND");
				lr.setMessage("You have entered a Invalid UserId");
				
				return new ResponseEntity<LoginResponse>(lr,HttpStatus.NOT_FOUND);	
					
			}
			
			if(loginUser.getPassword().toString().equals(l.getPassword())) {
			
                UserDto userDto=modelMapper.map(loginUser, UserDto.class);
                
				lr=LoginResponse.builder().user(userDto).build();
				
				System.out.println("login success");
				lr.setStatus("200");
				lr.setStatusMessage("OK");
				lr.setMessage("Login Success");
				LoginData ld=new LoginData(l.getUserId(),"Login Success",date,time);
				loginDataService.saveLoginData(ld);

				return new ResponseEntity<LoginResponse>(lr,HttpStatus.OK);	
				
			}else {
				
				LoginData ld=new LoginData(l.getUserId(),"Incorrect password",date,time);
				loginDataService.saveLoginData(ld);
				
				System.out.println("login failed"); 
				lr.setStatus("401");
				lr.setStatusMessage("Unauthorized");
				lr.setMessage("Wrong Password");
				

				return new ResponseEntity<LoginResponse>(lr,HttpStatus.UNAUTHORIZED);	
				
			}
			
		}else {
			lr.setStatus("400");
			lr.setStatusMessage("Bad Request");
			lr.setMessage("Error in Data Transfer");
			return new ResponseEntity<LoginResponse>(lr,HttpStatus.BAD_REQUEST);
		}
		
	}
	  
	
	@PostMapping("{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable String userId){
		
		UserDto ud=userService.fetchUserDetailsById(userId);
		return new ResponseEntity<UserDto>(ud,HttpStatus.OK);
	}
	
	@PutMapping("{userId}")
	public ResponseEntity<UserDto> updateUserDetails(@PathVariable String userId, @RequestBody UserDto userDto){
		
		UserDto user=userService.updateUser(userId, userDto);
		return new ResponseEntity<UserDto>(user,HttpStatus.OK);
		
	}

	

}
