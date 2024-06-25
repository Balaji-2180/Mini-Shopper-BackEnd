package com.example.demo.Controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.http.HttpHeaders;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

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
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Response.LoginResponse;

import com.example.demo.Service.LoginDataService;
import com.example.demo.Service.UserService;
import com.example.demo.Service.impl.UserServiceImpl;
import com.example.demo.dtos.JwtResponseDto;
import com.example.demo.dtos.LoginDto;
import com.example.demo.dtos.UserDto;
import com.example.demo.exception.LoginException;
import com.example.demo.exception.UnauthorizedException;

import com.example.demo.Service.CustomUserDetailsService;
import com.example.demo.security.JwtHelper;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpServletRequest;




@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/users")
public class LoginController {
    
    @Autowired 
    LoginDataService loginDataService;

    
    
    
    @Autowired
	private ModelMapper modelMapper;
    
    @Autowired
	private AuthenticationManager manager;
    @Autowired
	private CustomUserDetailsService userDetailService;

	@Autowired
	private JwtHelper jwtHelper;
    
    
    @Autowired
	private UserService userService;
    
   
	 
	@PostMapping("/loginUser")
	public ResponseEntity<JwtResponseDto> loginUser(@RequestBody LoginDto l) {
		System.out.println("in login controller");
		
		doAuthenticate(l.getUserId(), l.getPassword());
		
		//LoginResponse loginResponse=new LoginResponse();
		UserDetails userDetails = userDetailService.loadUserByUsername(l.getUserId());
		
		System.out.println(userDetails.getUsername()+"   "+userDetails.getPassword());

		// generate jwt token and refresh token
		String jwtToken = this.jwtHelper.generateToken(userDetails);
		String refreshToken = this.jwtHelper.generateRefreshToken(userDetails);
		
		System.out.println("jwt token in controller "+jwtToken+"  "+refreshToken);

		UserDto userDto = userService.fetchUserDetailsById(userDetails.getUsername());
		userDto.setUserId(userDetails.getUsername());

		System.out.println(userDto.toString());
		
		JwtResponseDto response = JwtResponseDto.builder().accessToken(jwtToken).refreshToken(refreshToken)
				.user(userDto).build();
		System.out.println(response.toString());
		
		
		return new ResponseEntity<JwtResponseDto>(response, HttpStatus.OK);

//		System.out.println(l.toString());
//		LocalDate date=LocalDate.now();
//		LocalTime time=LocalTime.now();
//		
//		LoginResponse lr=new LoginResponse();
//		lr.setStatus("");
//		if(l.getUserId()!=null && l.getPassword()!=null) {
//
//			User loginUser=userService.checkUserId(l.getUserId());
//			if(loginUser==null) {
//				
//				LoginData ld=new LoginData(l.getUserId(),"Invalid UserId and password",date,time);
//				loginDataService.saveLoginData(ld);
//				lr.setStatus("404");
//				lr.setStatusMessage("NOT_FOUND");
//				lr.setMessage("You have entered a Invalid UserId");
//				
//				return new ResponseEntity<LoginResponse>(lr,HttpStatus.NOT_FOUND);	
//					
//			}
//			
//			if(loginUser.getPassword().toString().equals(l.getPassword())) {
//			
//                UserDto userDto=modelMapper.map(loginUser, UserDto.class);
//                
//				lr=LoginResponse.builder().user(userDto).build();
//				
//				System.out.println("login success");
//				lr.setStatus("200");
//				lr.setStatusMessage("OK");
//				lr.setMessage("Login Success");
//				LoginData ld=new LoginData(l.getUserId(),"Login Success",date,time);
//				loginDataService.saveLoginData(ld);
//
//				return new ResponseEntity<LoginResponse>(lr,HttpStatus.OK);	
//				
//			}else {
//				
//				LoginData ld=new LoginData(l.getUserId(),"Incorrect password",date,time);
//				loginDataService.saveLoginData(ld);
//				
//				System.out.println("login failed"); 
//				lr.setStatus("401");
//				lr.setStatusMessage("Unauthorized");
//				lr.setMessage("Wrong Password");
//				
//
//				return new ResponseEntity<LoginResponse>(lr,HttpStatus.UNAUTHORIZED);	
//				
//			}
//			
//		}else {
//			lr.setStatus("400");
//			lr.setStatusMessage("Bad Request");
//			lr.setMessage("Error in Data Transfer");
//			return new ResponseEntity<LoginResponse>(lr,HttpStatus.BAD_REQUEST);
//		}
		
	}
	

	
	@PostMapping("/{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable String userId){
		System.out.println("in login controller get user by id");
		UserDto ud=userService.fetchUserDetailsById(userId);
		return new ResponseEntity<UserDto>(ud,HttpStatus.OK);
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUserDetails(@PathVariable String userId, @RequestBody UserDto userDto){
		System.out.println("in login controller update user details");
		
		UserDto user=userService.updateUser(userId, userDto);
		return new ResponseEntity<UserDto>(user,HttpStatus.OK);
		
	}
	
	
	
	
	private void doAuthenticate(String email, String password) {
		// check if email and password are correct
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
		try {
			manager.authenticate(authentication);
		} catch (BadCredentialsException e) {
			// throw this exception if invalid email or password
			throw new UnauthorizedException("Invalid email or password!");
		}
	}

	

}
