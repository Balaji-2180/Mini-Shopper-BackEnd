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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.Entity.LoginData;
import com.example.demo.Entity.Orders;
import com.example.demo.Entity.User;
import com.example.demo.Repository.LoginDataRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Response.LoginResponse;
import com.example.demo.Service.impl.UserServiceImpl;
import com.example.demo.dtos.LoginDto;
import com.example.demo.exception.LoginException;




@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/users/")
public class LoginController {
	// just checking git bash working
    @Autowired
	UserRepository ur;
    
    @Autowired
    LoginDataRepository loginRepo;
    
    @Autowired
    UserServiceImpl userServiceImpl;
    
    
    
    @GetMapping("loginUser")
    public LoginDto getuser() {
    	LoginDto ld=new LoginDto();
    	ld.setPassword("12edwedD");
    	ld.setUserId("GopalaKrishnan@gmail.com");
    	return ld;
    }
	 
	@PostMapping("loginUser")
	public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginDto l) {

		System.out.println(l.toString());
		LocalDate date=LocalDate.now();
		LocalTime time=LocalTime.now();
		
		LoginResponse lr=new LoginResponse();
		
		if(l.getUserId()!=null && l.getPassword()!=null) {

			
			User loginUser=userServiceImpl.checkUserId(l.getUserId());
			if(loginUser==null) {
				//System.out.println("the objecct is null");
				
				LoginData ld=new LoginData(l.getUserId(),"Invalid UserId and password",date,time);
				loginRepo.save(ld);
				lr.setStatus("404");
				lr.setStatusMessage("NOT_FOUND");
				lr.setMessage("You have entered a Invalid UserId");
				
				return new ResponseEntity<LoginResponse>(lr,HttpStatus.NOT_FOUND);	
				//return ResponseEntity.status(HttpStatus.OK).body("User Not Found");		
			}
			
			if(loginUser.getPassword().toString().equals(l.getPassword())) {
				//res=new ResponseEntity<User>(loginUser,HttpStatus.OK);
				System.out.println("login success");
				lr.setStatus("200");
				lr.setStatusMessage("OK");
				lr.setMessage("Login Success");
				
				LoginData ld=new LoginData(l.getUserId(),"Login Success",date,time);
				loginRepo.save(ld);

				return new ResponseEntity<LoginResponse>(lr,HttpStatus.OK);	
				
//				return ResponseEntity.status(HttpStatus.OK).body("Login Success");
			}else {
				
				LoginData ld=new LoginData(l.getUserId(),"Incorrect password",date,time);
				loginRepo.save(ld);
				
				System.out.println("login failed"); 
				lr.setStatus("401");
				lr.setStatusMessage("Unauthorized");
				lr.setMessage("Wrong Password");
				

				return new ResponseEntity<LoginResponse>(lr,HttpStatus.UNAUTHORIZED);	
				
				//return ResponseEntity.status(HttpStatus.OK).body("Login Failed");
			}
			
		}else {
			lr.setStatus("400");
			lr.setStatusMessage("Bad Request");
			lr.setMessage("Error in Data Transfer");
			return new ResponseEntity<LoginResponse>(lr,HttpStatus.BAD_REQUEST);
		}
		
//		System.out.println("entity response "+res);
	}
	
	@PostMapping("testHere")
	public ResponseEntity<LoginException> testHere() {
		LoginException le=new LoginException();
		le.setStatus("200");
		le.setMessage("Login Success");
		
		return new ResponseEntity<LoginException>(le,HttpStatus.OK);
		
	}
	
	
	
	
	
	@GetMapping("allUsers")
	public ResponseEntity<List<User>> getAllUsers(){
		System.out.println("getting into all users");
		return new ResponseEntity<List<User>>(ur.findAll(),HttpStatus.OK); 
	}
	

}
