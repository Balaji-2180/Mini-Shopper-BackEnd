package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.Entity.User;
import com.example.demo.Response.RegisterResponse;
import com.example.demo.Service.UserService;
import com.example.demo.Service.impl.UserServiceImpl;



@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/users/")
public class RegisterController {
    
    @Autowired
    UserService userService; 


	@PostMapping("newUser") 
	public ResponseEntity<RegisterResponse> saveUser(@RequestBody User u) {		
		RegisterResponse rr=new RegisterResponse();
		//u.setUserId(null);
		if(u.getFirstName()!=null&&u.getLastName()!=null&&u.getEmail()!=null&&u.getPassword()!=null&&u.getUserId()!=null) {
//				System.out.println("user entered "+u.toString());
			
				User loginUser=userService.checkUserId(u.getUserId());
				if(loginUser==null) {
					userService.saveUser(u);
					rr.setStatus("201");
					rr.setStatusMessage("Created");
					rr.setMessage("User Created SUccessfully");
					
//					System.out.println("new user created");
					
					return new ResponseEntity<RegisterResponse>(rr,HttpStatus.CREATED);

				}else {
					rr.setStatus("208");
					rr.setStatusMessage("Already Reported");
					rr.setMessage("Entered Email address already exist, please try again with other Email address");
					
//					System.out.println("already exist");
					
					return new ResponseEntity<RegisterResponse>(rr,HttpStatus.ALREADY_REPORTED);
				}
				
		}else {
			rr.setStatus("400");
			rr.setStatusMessage("Bad Request");
			rr.setMessage("Error in Data Transfer");
		
			return new ResponseEntity<RegisterResponse>(rr,HttpStatus.BAD_REQUEST);
		}
	}

}
